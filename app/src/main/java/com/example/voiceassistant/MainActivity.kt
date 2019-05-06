package com.example.voiceassistant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Database.database
import com.example.voiceassistant.MainActivityFragment.CalendarFragment
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.MainActivityFragment.WeatherHistoryFragment
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val voiceFragment = VoiceAssistanceFragment.newInstance()
    val weatherFragment = WeatherHistoryFragment.newInstance()
    val calendarFragment = CalendarFragment.newInstance()
    var activeFragment: Fragment = voiceFragment

    private val RECORD_REQUEST_CODE = 101

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.voice_assistance_item -> {
                openFragment(voiceFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.weather_history_item -> {
                openFragment(weatherFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.calendar_item -> {
                openFragment(calendarFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        toolbar.title = getString(R.string.voiceAssistant)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().add(R.id.container, weatherFragment, "2").hide(weatherFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, calendarFragment, "3").hide(calendarFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, voiceFragment, "1").commit()

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)){
                // do something?
            }else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    RECORD_REQUEST_CODE)
            }
        }

        // add some task to calendar
//        addDummyDataToCalendar()
        val tasks = TaskRepository(this).findTaskByDate("07/05/2019")

        tasks.forEach {
            Log.d(RetrofitClient.TAG, "Text: ${it.text} date: ${it.date}")
        }
    }

    fun addDummyDataToCalendar(){
        TaskRepository(this).insert(TaskPOJO(false, "Test 1", "06/05/2019"))
        TaskRepository(this).insert(TaskPOJO(false, "Test 2", "06/05/2019"))
        TaskRepository(this).insert(TaskPOJO(false, "Test 3", "06/05/2019"))
        TaskRepository(this).insert(TaskPOJO(false, "Test 4", "06/05/2019"))
        TaskRepository(this).insert(TaskPOJO(false, "Test 5", "06/05/2019"))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        R.id.action_settings ->{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}