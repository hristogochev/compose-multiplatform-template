package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.Foundation.NSCharacterSet
import platform.Foundation.NSError
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.setValue
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoDuplex
import platform.UIKit.UIPrintInteractionController
import platform.UIKit.viewPrintFormatter
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

@Composable
actual fun rememberPrinterManager(onReceivedError: () -> Unit): PrinterManager {
    val onReceivedErrorUpdated by rememberUpdatedState(onReceivedError)
    return remember {
        object : PrinterManager {
            override suspend fun print(
                htmlUrl: String,
                bearerToken: String?,
                queryParameters: Map<String, String>,
                jobName: String,
            ) {
                withContext(Dispatchers.IO) {
                    val urlString = buildUrlWithQueryParameters(htmlUrl, queryParameters)

                    val urlRequest = createRequestWithBearerToken(urlString, bearerToken) ?: run {
                        println("Printing: urlRequestError")
                        onReceivedErrorUpdated()
                        return@withContext
                    }

                    // Create the NSURLRequest with Bearer Token if provided
                    // Prepare the WebView and load the request

                    withContext(Dispatchers.Main) {
                        WKWebView().apply {
                            this.navigationDelegate =
                                object : NSObject(), WKNavigationDelegateProtocol {
                                    override fun webView(
                                        webView: WKWebView,
                                        didFinishNavigation: WKNavigation?,
                                    ) {
                                        // Once content has loaded, proceed with printing
                                        printWebViewContent(webView, jobName)
                                    }

                                    override fun webView(
                                        webView: WKWebView,
                                        didFailNavigation: WKNavigation?,
                                        withError: NSError,
                                    ) {
                                        // Handle loading error
                                        onReceivedErrorUpdated()
                                    }
                                }
                        }.also {
                            it.loadRequest(urlRequest)
                        }
                    }
                }
            }

            private fun createRequestWithBearerToken(
                urlString: String,
                bearerToken: String?,
            ): NSURLRequest? {
                val encodedUrlString = encodeUrl(urlString) ?: run {
                    println("Printing: unable to encode url")
                    return null
                }

                // Create the base URL
                println("Printing: URL: $encodedUrlString")
                val url = NSURL.URLWithString(encodedUrlString) ?: kotlin.run {
                    println("Printing: Unable to create url string")
                    return null
                }

                // Create the NSMutableURLRequest and add the Authorization header if the token is available
                val request = NSMutableURLRequest(url)

                bearerToken?.also {
                    // Add Bearer token to the request headers
                    request.setValue("Bearer $it", forHTTPHeaderField = "Authorization")
                }

                return request
            }

            private fun printWebViewContent(webView: WKWebView, jobName: String) {
                val printInfo = UIPrintInfo.printInfo().apply {
                    this.jobName = jobName
                    this.duplex = UIPrintInfoDuplex.UIPrintInfoDuplexNone
                }

                val printInteractionController =
                    UIPrintInteractionController.sharedPrintController()
                val printFormatter = webView.viewPrintFormatter()

                printInteractionController.printFormatter = printFormatter
                printInteractionController.printInfo = printInfo

                printInteractionController.presentAnimated(
                    animated = true
                ) { _, completed, error ->
                    if (!completed || error != null) {
                        onReceivedErrorUpdated()
                    }
                }
            }

            fun encodeUrl(urlString: String): String? {
                val nsString: NSString = urlString as NSString

                // Use addingPercentEncodingWithAllowedCharacters to encode the URL
                val encoded = nsString.stringByAddingPercentEncodingWithAllowedCharacters(
                    NSCharacterSet.URLQueryAllowedCharacterSet()
                )

                return encoded
            }
        }
    }
}