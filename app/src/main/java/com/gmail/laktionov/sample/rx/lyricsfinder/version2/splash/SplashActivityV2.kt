package com.gmail.laktionov.sample.rx.lyricsfinder.version2.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.search.SearchActivityV2
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.io.IOException

class SplashActivityV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        showTestWebView()
        delayStart()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showTestWebView() {
        val localHtmlString = try {
            val inputStream = assets.open("testHtml.html")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (exp: IOException) {
            ""
        }
        with(lyricWebView) {
            visibility = View.VISIBLE
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                    handler.proceed()
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView?, url: String, message: String, result: JsResult?): Boolean {
                    Log.i("TESTACTION", message)
                    result?.confirm()
                    return true
                }
            }

            loadData(localHtmlString, MIME, ENCODING)
        }
    }

    private fun delayStart() {
        launch(UI) {
            delay(2)
            startActivity(Intent(this@SplashActivityV2, SearchActivityV2::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    companion object {
        const val ENCODING = "utf-8"
        const val MIME = "text/html"
    }
}
