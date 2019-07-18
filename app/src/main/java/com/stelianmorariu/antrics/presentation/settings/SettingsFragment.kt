/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.stelianmorariu.antrics.presentation.commons.ThemeHelper


class SettingsFragment : PreferenceFragmentCompat() {

    /**
     * Called during [.onCreate] to supply the preferences for this fragment.
     * Subclasses are expected to call [.setPreferenceScreen] either
     * directly or via helper methods such as [.addPreferencesFromResource].
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     * this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     * [PreferenceScreen] with this key.
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.stelianmorariu.antrics.R.xml.preferences, rootKey)

        val themePreference = findPreference<Preference>("theme")
        themePreference?.setOnPreferenceChangeListener { pref, newValue ->
            val themeOption = newValue as String
            ThemeHelper.applyTheme(themeOption)
            true
        }
    }

}
