/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.settings

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.stelianmorariu.antrics.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var upButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        upButton = findViewById(R.id.btn_up)
        upButton.setOnClickListener { v ->
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            finish()
        }

    }

}