package com.example.voiceassistant

import android.content.DialogInterface
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.widget.Toast
import com.example.voiceassistant.Database.WeatherRepository
import com.example.voiceassistant.MainActivityFragment.WeatherHistoryFragment

class SettingsActivity: AppCompatPreferenceActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutInflater.inflate(R.layout.toolbar, findViewById(android.R.id.content))
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        fixPadding()
        setSupportActionBar(toolbar)
        setupActionBar()

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        addPreferencesFromResource(R.xml.pref_general)

        val historyButton = findPreference(getString(R.string.clearHistoryButton))
        historyButton.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            var isHistoryDeleted = false

            AlertDialog.Builder(this)
                .setTitle("Delete History")
                .setMessage("Are you sure you want to delete all the history?")
                .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this, "History deleted", Toast.LENGTH_SHORT).show()
                    WeatherRepository(this).deleteAll()
                    WeatherHistoryFragment.historial.clear()
                    WeatherHistoryFragment.viewAdapter.notifyDataSetChanged()
                    isHistoryDeleted = true
                })
                .setNegativeButton(android.R.string.no, DialogInterface.OnClickListener { dialog, which ->
                    isHistoryDeleted = false
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

            isHistoryDeleted
        }
    }
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }


    private fun fixPadding(){
        val horizontalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
        val verticalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
        val topMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            (resources.getDimension(R.dimen.preference_padding).toInt() + 30).toFloat(),
            resources.displayMetrics).toInt()

        listView.setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin)
    }
}