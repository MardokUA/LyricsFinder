package com.gmail.laktionov.sample.rx.lyricsfinder.version2.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.search.SearchActivityV2
import kotlinx.android.synthetic.main.activity_pin.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class SplashActivityV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        setContentView(R.layout.activity_pin)
//        showTestWebView()
//        delayStart()
        pinZero.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinOne.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinTwo.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinThree.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinFour.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinFive.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinSix.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinSeven.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinEight.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinNine.setOnClickListener { onPinButtonClickListener(it as AppCompatButton) }
        pinBackspace.setOnClickListener { pinZone.text = null }
    }

    private fun onPinButtonClickListener(buttonView: AppCompatButton) {
        when (buttonView.id) {
            R.id.pinZero -> updatePinField(pinOne.text.toString())
            R.id.pinOne -> updatePinField(pinOne.text.toString())
            R.id.pinTwo -> updatePinField(pinTwo.text.toString())
            R.id.pinThree -> updatePinField(pinThree.text.toString())
            R.id.pinFour -> updatePinField(pinFour.text.toString())
            R.id.pinFive -> updatePinField(pinFive.text.toString())
            R.id.pinSix -> updatePinField(pinSix.text.toString())
            R.id.pinSeven -> updatePinField(pinSeven.text.toString())
            R.id.pinEight -> updatePinField(pinEight.text.toString())
            R.id.pinNine -> updatePinField(pinNine.text.toString())
        }
    }

    private fun updatePinField(number: String) {
        pinZone.text.toString().let { text ->
            when {
                text.isEmpty() -> pinZone.setText(number)
                else -> pinZone.setText(concatText(text, number))
            }
        }
    }


    private fun concatText(text: String, number: String): String = "$text$number"

    @SuppressLint("SetJavaScriptEnabled")
    private fun showTestWebView() {
//        val localHtmlString = try {
//            val inputStream = assets.open("testHtml.html")
//            val buffer = ByteArray(inputStream.available())
//            inputStream.read(buffer)
//            inputStream.close()
//            String(buffer)
//        } catch (exp: IOException) {
//            ""
//        }
//        with(lyricWebView) {
//            visibility = View.VISIBLE
//            settings.javaScriptEnabled = true
//            webViewClient = object : WebViewClient() {
//                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//                    handler?.proceed()
//                }
//            }
//            webChromeClient = object : WebChromeClient() {
//                override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
//                    Log.i("TESTACTION", message)
//                    result?.confirm()
//                    return true
//                }
//            }
//            loadData(localHtmlString, MIME, ENCODING)
//        }
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
