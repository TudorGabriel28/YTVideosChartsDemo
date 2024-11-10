package com.example.ytvideoscharts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.ytvideoscharts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val VIDEO_ID = "V2KCAfHjySQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )

//        YOUTUBE VIDEO EMBEDDING

        val webView = findViewById<WebView>(R.id.youtube_webview)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        val html = """
            <html>
                <body style="margin:0; padding:0;">
                    <iframe id="player" type="text/html" width="100%" height="100%"
                        src="https://www.youtube.com/embed/$VIDEO_ID?enablejsapi=1"
                        frameborder="0" allowfullscreen>
                    </iframe>
                </body>
            </html>
        """

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView, url: String
            ): Boolean {
                if (Uri.parse(url).host != "www.youtube.com") {
                    val intent = Intent(
                        Intent.ACTION_VIEW, Uri.parse(url)
                    )
                    startActivity(intent)
                    return true
                }
                return false
            }
        }

        webView.loadData(
            html, "text/html", "utf-8"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}