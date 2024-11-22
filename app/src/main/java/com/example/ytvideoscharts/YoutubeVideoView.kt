package com.example.ytvideoscharts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class YouTubeVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val webView: WebView

    init {
        webView = WebView(context)
        addView(webView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        setupWebView()
    }

    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("https://www.youtube.com")) {
                    return false
                }
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) // Open external links
                return true
            }
        }
    }

    fun loadVideo(videoId: String) {
        val html = """
            <html>
                <body style="margin:0; padding:0;">
                    <iframe id="player" type="text/html" width="100%" height="100%"
                        src="https://www.youtube.com/embed/$videoId?enablejsapi=1"
                        frameborder="0" allowfullscreen>
                    </iframe>
                </body>
            </html>
        """
        webView.loadData(html, "text/html", "utf-8")
    }
}
