package com.example.voiceassistant

import android.Manifest
import android.content.Context
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
import com.example.voiceassistant.MainActivityFragment.TodoListFragment
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.MainActivityFragment.WeatherHistoryFragment
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    val voiceFragment = VoiceAssistanceFragment.newInstance()
    val weatherFragment = WeatherHistoryFragment.newInstance()
    val todoListFragment = TodoListFragment.newInstance()
    val calendarFragment = CalendarFragment.newInstance()
    var activeFragment: Fragment = voiceFragment

    lateinit var toolbar: Toolbar

    private val RECORD_REQUEST_CODE = 101

    val PERMISSION_ALL = 1
    val PERMISSIONS = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.voice_assistance_item -> {
                openFragment(voiceFragment)
                toolbar.title = getString(R.string.voiceAssistant)
                return@OnNavigationItemSelectedListener true
            }
            R.id.weather_history_item -> {
                openFragment(weatherFragment)
                toolbar.title = getString(R.string.history)

                return@OnNavigationItemSelectedListener true
            }
            R.id.todo_list_item -> {
                openFragment(todoListFragment)
                toolbar.title = getString(R.string.todolist_title)

                return@OnNavigationItemSelectedListener true
            }
            R.id.calendar_item -> {
                openFragment(calendarFragment)
                toolbar.title = getString(R.string.calendar)

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
        toolbar = findViewById(R.id.my_toolbar)
        toolbar.title = getString(R.string.voiceAssistant)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().add(R.id.container, weatherFragment, "2").hide(weatherFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, todoListFragment, "3").hide(todoListFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, calendarFragment, "4").hide(calendarFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, voiceFragment, "1").commit()


        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }
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

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }}