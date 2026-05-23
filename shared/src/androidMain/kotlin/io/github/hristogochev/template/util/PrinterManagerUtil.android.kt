package io.github.hristogochev.template.util

import android.content.Context
import android.print.PrintManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPrinterManager(onReceivedError: () -> Unit): PrinterManager {
    val context = LocalContext.current

    val onReceivedErrorUpdated by rememberUpdatedState(onReceivedError)

    return remember(context) {
        object : PrinterManager {
            override suspend fun print(
                htmlUrl: String,
                bearerToken: String?,
                queryParameters: Map<String, String>,
                jobName: String,
            ) {

                val headers = mutableMapOf<String, String>()

                bearerToken?.also {
                    // Create custom headers with the Bearer Token
                    headers += mapOf("Authorization" to "Bearer $it")
                }

                // Build the full URL with query parameters
                val fullUrl = buildUrlWithQueryParameters(htmlUrl, queryParameters)

                val webView = WebView(context).also {
                    it.loadUrl(fullUrl, headers)
                }

                // Wait for the content to load before printing
                webView.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String?) {
                        // Start printing after the page is fully loaded
                        printWebViewContent(context, webView, jobName)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?,
                    ) {
                        super.onReceivedError(view, request, error)
                        println("Error loading page: $error")
                        onReceivedErrorUpdated()
                    }
                }
            }

            private fun printWebViewContent(context: Context, webView: WebView, jobName: String) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                val printAdapter = webView.createPrintDocumentAdapter("MyHTMLDocument")

                printManager.print(jobName, printAdapter, null)
            }
        }
    }
}