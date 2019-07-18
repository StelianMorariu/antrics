/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.commons

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat

class ThemeHelper {

    companion object {
        val LIGHT_MODE = "light"
        val DARK_MODE = "dark"
        val DEFAULT_MODE = "default"

        fun applyTheme(themePref: String) {
            when (themePref) {
                LIGHT_MODE -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                DARK_MODE -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    if (BuildCompat.isAtLeastQ()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    }
                }
            }
        }

    }
}