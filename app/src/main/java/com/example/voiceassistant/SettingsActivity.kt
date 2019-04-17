package com.example.voiceassistant

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.voiceassistant.Database.WeatherRepository
import com.example.voiceassistant.MainActivityFragment.WeatherHistoryFragment

class SettingsActivity: AppCompatPreferenceActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        addPreferencesFromResource(R.xml.pref_general)

        val historyButton = findPreference(getString(R.string.clearHistoryButton))
        historyButton.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(this, "History deleted", Toast.LENGTH_SHORT).show()
            WeatherRepository(this).deleteAll()
            WeatherHistoryFragment.historial.clear()
            WeatherHistoryFragment.viewAdapter.notifyDataSetChanged()
            true
        }
    }
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}