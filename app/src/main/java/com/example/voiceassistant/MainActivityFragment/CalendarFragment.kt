package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.TimeUtils

class CalendarFragment : Fragment(){

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment , container, false)

        val calendar = view.findViewById<CalendarView>(R.id.calendar)
        val todolistTitle = view.findViewById<TextView>(R.id.todolist_date)
        todolistTitle.text = TimeUtils.getTodayDate()

        calendar.setOnDateChangeListener{view, year, month, dayOfMonth ->
            todolistTitle.text = "$dayOfMonth/${month + 1}/$year"
        }

        return view
    }
}