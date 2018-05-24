package com.gmail.laktionov.sample.rx.lyricsfinder.version2.pin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import kotlinx.android.synthetic.main.activity_pin.*

/**
 * Created by: Roman Laktionov
 * Date: 22.05.18
 */

class PinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        setupView()
    }

    private fun setupView() {
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
}