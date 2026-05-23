package io.github.hristogochev.template.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    path: String,
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T> =
    try {
        val response = request {
            url(path)
            block()
        }
        ApiResponse.Success(response.body())
    } catch (e: RedirectResponseException) {
        parseHttpError(path, e.response.status.value, e.errorBody())
    } catch (e: ClientRequestException) {
        parseHttpError(path, e.response.status.value, e.errorBody())
    } catch (e: ServerResponseException) {
        parseHttpError(path, e.response.status.value, e.errorBody())
    } catch (_: IOException) {
        ApiResponse.Error.NetworkError(path)
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(path, e.message)
    } catch (e: JsonConvertException) {
        ApiResponse.Error.SerializationError(path, e.message)
    } catch (_: Exception) {
        ApiResponse.Error.NetworkError(path)
    }

fun parseHttpError(url: String, code: Int, message: String?) = when (code) {
    401 -> ApiResponse.Error.HttpError.Unauthorized(url, code, message)
    423 -> ApiResponse.Error.HttpError.Locked(url, code, message)
    else -> ApiResponse.Error.HttpError.Unknown(url, code, message)
}

suspend inline fun ResponseException.errorBody(): String? =
    try {
        response.body()
    } catch (_: SerializationException) {
        null
    } catch (_: JsonConvertException) {
        null
    } catch (_: Exception) {
        null
    }

sealed class ApiResponse<out T> {
    data class Success<T>(val body: T) : ApiResponse<T>()

    sealed class Error : ApiResponse<Nothing>() {
        abstract val url: String

        sealed class HttpError : Error() {
            abstract val code: Int
            abstract val message: String?

            data class Unauthorized(
                override val url: String,
                override val code: Int,
                override val message: String?
            ) :
                HttpError()

            data class Unknown(
                override val url: String,
                override val code: Int,
                override val message: String?
            ) : HttpError()

            data class Locked(
                override val url: String,
                override val code: Int,
                override val message: String?
            ) : HttpError()
        }

        data class NetworkError(override val url: String) : Error()
        data class SerializationError(override val url: String, val message: String?) : Error()
    }
}

inline fun <R, T> ApiResponse<T>.map(transform: (value: T) -> R): ApiResponse<R> {
    return when (this) {
        is ApiResponse.Error -> this
        is ApiResponse.Success -> ApiResponse.Success(transform(this.body))
    }
}


inline fun <R, T : R> ApiResponse<T>.getOptionalOrElse(onFailure: (error: ApiResponse.Error) -> R): R? {
    return when (this) {
        is ApiResponse.Error -> {
            println("getOptionalOrElse error: $this")
            if (this is ApiResponse.Error.HttpError && this.code == 404) null
            else onFailure(this)
        }

        is ApiResponse.Success -> this.body
    }
}

inline fun <reified T> ApiResponse<T>.ifError(op: (ApiResponse.Error) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        op(this)
    }
    return this
}


inline fun <reified T> ApiResponse<T>.ifSuccess(op: (T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        op(this.body)
    }
    return this
}


inline fun <R, T : R> ApiResponse<T>.getOrElse(onFailure: (error: ApiResponse.Error) -> R): R {
    return when (this) {
        is ApiResponse.Error -> {
            println("getOrElse error: $this")
            onFailure(this)
        }

        is ApiResponse.Success -> this.body
    }
}

fun <T> ApiResponse<T>.getOrNull(): T? {
    return when (this) {
        is ApiResponse.Error -> {
            println("getOrNull error: $this")
            null
        }

        is ApiResponse.Success -> this.body
    }
}
