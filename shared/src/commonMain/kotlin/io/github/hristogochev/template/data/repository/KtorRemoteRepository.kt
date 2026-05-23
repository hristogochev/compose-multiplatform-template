package io.github.hristogochev.template.data.repository

import io.github.hristogochev.template.data.entity.response.CityEntity
import io.github.hristogochev.template.data.entity.response.RegisteredResponseEntity
import io.github.hristogochev.template.data.entity.response.SuccessResponseEntity
import io.github.hristogochev.template.data.entity.response.UserDetailsEntity
import io.github.hristogochev.template.data.entity.response.VersionEntity
import io.github.hristogochev.template.data.mapper.request.toEntity
import io.github.hristogochev.template.data.entity.response.SmsSuccessResponseEntity
import io.github.hristogochev.template.data.entity.response.TokenResponseEntity
import io.github.hristogochev.template.data.mapper.request.toLoginSMSRequestEntity
import io.github.hristogochev.template.data.mapper.request.toLoginValidateRequestEntity
import io.github.hristogochev.template.data.mapper.request.toRegisterSMSRequestEntity
import io.github.hristogochev.template.data.mapper.request.toRegisterValidateRequestEntity
import io.github.hristogochev.template.data.mapper.request.toRegisteredRequestEntity
import io.github.hristogochev.template.data.mapper.response.toCity
import io.github.hristogochev.template.data.mapper.response.toRegisteredResponse
import io.github.hristogochev.template.data.mapper.response.toSmsSuccessResponse
import io.github.hristogochev.template.data.mapper.response.toSuccessResponse
import io.github.hristogochev.template.data.mapper.response.toTokenResponse
import io.github.hristogochev.template.data.mapper.response.toUserDetails
import io.github.hristogochev.template.data.mapper.response.toVersion
import io.github.hristogochev.template.data.route.HttpRoutes
import io.github.hristogochev.template.domain.entity.auth.UserDetails
import io.github.hristogochev.template.domain.entity.dto.Version
import io.github.hristogochev.template.domain.entity.request.RegisteredRequest
import io.github.hristogochev.template.domain.entity.response.RegisteredResponse
import io.github.hristogochev.template.domain.entity.response.SuccessResponse
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.util.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.hristogochev.template.domain.entity.dto.City
import io.github.hristogochev.template.domain.entity.request.CreateDeviceTokenRequest
import io.github.hristogochev.template.domain.entity.request.LoginSMSRequest
import io.github.hristogochev.template.domain.entity.request.LoginValidateRequest
import io.github.hristogochev.template.domain.entity.request.RegisterSMSRequest
import io.github.hristogochev.template.domain.entity.request.RegisterValidateRequest
import io.github.hristogochev.template.domain.entity.request.UpdateFullNameRequest
import io.github.hristogochev.template.domain.entity.request.UpdateNotificationsEnabledRequest
import io.github.hristogochev.template.domain.entity.response.SmsSuccessResponse
import io.github.hristogochev.template.domain.entity.response.TokenResponse
import io.github.hristogochev.template.util.map
import io.github.hristogochev.template.util.safeRequest
import kotlinx.coroutines.IO

class KtorRemoteRepository(private val client: HttpClient) : RemoteRepository {
    companion object {
        private const val UNABLE_TO_DESERIALIZE = "Unable to deserialize output"
        private const val UNABLE_TO_SERIALIZE = "Unable to serialize input"
    }

    override suspend fun getVersion(): ApiResponse<Version> {
        return withContext(Dispatchers.IO) {
            client.safeRequest<VersionEntity>(HttpRoutes.VERSION) {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
            }.map {
                it.toVersion()
            }
        }
    }

    override suspend fun sendRegisteredRequest(registeredRequest: RegisteredRequest): ApiResponse<RegisteredResponse> {
        return withContext(Dispatchers.IO) {
            val request = registeredRequest.toRegisteredRequestEntity()
            client.safeRequest<RegisteredResponseEntity>(HttpRoutes.GET_REGISTERED) {
                method = HttpMethod.Get
                parameter("phone_number", request.phoneNumber)
            }.map { entity ->
                entity.toRegisteredResponse()
            }
        }
    }

    override suspend fun sendRegisterSmsRequest(registerSMSRequest: RegisterSMSRequest): ApiResponse<SmsSuccessResponse> {
        return withContext(Dispatchers.IO) {
            val request = registerSMSRequest.toRegisterSMSRequestEntity()
            client.safeRequest<SmsSuccessResponseEntity>(HttpRoutes.POST_REGISTER_SMS) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(request)
                header("Phone-Number", request.phoneNumber)
            }.map {
                it.toSmsSuccessResponse()
            }
        }
    }

    override suspend fun sendRegisterValidateRequest(registerValidateRequest: RegisterValidateRequest): ApiResponse<TokenResponse> {
        return withContext(Dispatchers.IO) {
            val request = registerValidateRequest.toRegisterValidateRequestEntity()
            client.safeRequest<TokenResponseEntity>(HttpRoutes.POST_REGISTER_VERIFY) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(request)
            }.map {
                it.toTokenResponse()
            }
        }
    }

    override suspend fun sendLoginSmsRequest(loginSMSRequest: LoginSMSRequest): ApiResponse<SmsSuccessResponse> {
        return withContext(Dispatchers.IO) {
            val request = loginSMSRequest.toLoginSMSRequestEntity()
            client.safeRequest<SmsSuccessResponseEntity>(HttpRoutes.POST_LOGIN_SMS) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(request)
                header("Phone-Number", request.phoneNumber)
            }.map {
                it.toSmsSuccessResponse()
            }
        }
    }

    override suspend fun sendLoginValidateRequest(loginValidateRequest: LoginValidateRequest): ApiResponse<TokenResponse> {
        return withContext(Dispatchers.IO) {
            val request = loginValidateRequest.toLoginValidateRequestEntity()
            client.safeRequest<TokenResponseEntity>(HttpRoutes.POST_LOGIN_VERIFY) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(request)
            }.map {
                it.toTokenResponse()
            }
        }
    }

    override suspend fun sendLogoutRequest(token: String): ApiResponse<SuccessResponse> {
        return withContext(Dispatchers.IO) {
            client.safeRequest<SuccessResponseEntity>(HttpRoutes.POST_LOGOUT) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.map {
                it.toSuccessResponse()
            }
        }
    }

    override suspend fun getUserDetails(token: String): ApiResponse<UserDetails> {
        return withContext(Dispatchers.IO) {
            client.safeRequest<UserDetailsEntity>(
                HttpRoutes.GET_USER_DETAILS
            ) {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.map {
                it.toUserDetails()
            }
        }
    }

    override suspend fun patchUserFullName(
        token: String,
        request: UpdateFullNameRequest,
    ): ApiResponse<SuccessResponse> {
        val requestEntity = request.toEntity()
        return withContext(Dispatchers.IO) {
            client.safeRequest<SuccessResponseEntity>(HttpRoutes.PATCH_FULL_NAME) {
                method = HttpMethod.Patch
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(requestEntity)
            }.map {
                it.toSuccessResponse()
            }
        }
    }

    override suspend fun patchUserNotificationsEnabled(
        token: String,
        request: UpdateNotificationsEnabledRequest,
    ): ApiResponse<SuccessResponse> {
        val requestEntity = request.toEntity()
        return withContext(Dispatchers.IO) {
            client.safeRequest<SuccessResponseEntity>(HttpRoutes.PATCH_NOTIFICATIONS_ENABLED) {
                method = HttpMethod.Patch
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(requestEntity)
            }.map {
                it.toSuccessResponse()
            }
        }
    }

    override suspend fun getStatus(token: String): ApiResponse<SuccessResponse> {
        return withContext(Dispatchers.IO) {
            client.safeRequest<SuccessResponseEntity>(HttpRoutes.GET_STATUS) {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.map { entity ->
                entity.toSuccessResponse()
            }
        }
    }

    override suspend fun createDeviceToken(
        token: String,
        request: CreateDeviceTokenRequest,
    ): ApiResponse<SuccessResponse> {
        val requestEntity = request.toEntity()
        return withContext(Dispatchers.IO) {
            client.safeRequest<SuccessResponseEntity>(HttpRoutes.CREATE_DEVICE_TOKEN) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
                setBody(requestEntity)
            }.map {
                it.toSuccessResponse()
            }
        }
    }

    override suspend fun getCities(): ApiResponse<List<City>> {
        return withContext(Dispatchers.IO) {
            client.safeRequest<List<CityEntity>>(HttpRoutes.GET_ALL_CITIES) {
                method = HttpMethod.Get
                contentType(ContentType.Application.Json)
            }.map {
                it.map { entity ->
                    entity.toCity()
                }
            }
        }
    }
}