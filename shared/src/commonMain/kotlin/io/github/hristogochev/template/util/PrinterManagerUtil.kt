package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberPrinterManager(onReceivedError: () -> Unit): PrinterManager

interface PrinterManager {
    suspend fun print(
        htmlUrl: String,
        bearerToken: String? = null,
        queryParameters: Map<String, String> = emptyMap(),
        jobName: String,
    )

    suspend fun buildUrlWithQueryParameters(
        url: String,
        queryParameters: Map<String, String>,
    ): String {
        if (queryParameters.isEmpty()) {
            return url
        }
        val queryString = queryParameters.entries.joinToString("&") {
            "${it.key}=${it.value}"
        }

        return "$url?$queryString"
    }
}
