package com.example.voiceassistant.MainActivityFragment

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import com.example.voiceassistant.Activity.TodoListActivity
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Viewholder.HorizontalDivider
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class CalendarFragment : Fragment(){

    private val PROJECTION_ID_INDEX: Int = 0
    private val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
    private val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
    private val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

    private val EVENT_PROJECTION: Array<String> = arrayOf(
        CalendarContract.Calendars._ID,
        CalendarContract.Calendars.ACCOUNT_NAME,
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
        CalendarContract.Calendars.OWNER_ACCOUNT)

    lateinit var viewManager : RecyclerView.LayoutManager
    lateinit var todolistRecyclerView: RecyclerView

    var date = TimeUtils.getTodayDate()
    var todolist = mutableListOf<TaskPOJO>()

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
        lateinit var viewAdapter : RecyclerView.Adapter<TodoListViewHolder>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment , container, false)
        val calendar = view.findViewById<CalendarView>(R.id.calendar)
        val todolistTitle = view.findViewById<TextView>(R.id.todolist_date)
        todolistTitle.text = date

        val viewAllBtn = view.findViewById<Button>(R.id.view_all_button)

        calendar.setOnDateChangeListener{ _ , year, month, dayOfMonth ->
            date = getDataFormatted(dayOfMonth, month + 1, year)
            viewAdapter.notifyDataSetChanged()
        }

        viewAllBtn.setOnClickListener {
            val intent = Intent(activity, TodoListActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TodoListAdapter(todolist)

        todolistRecyclerView = view.findViewById<RecyclerView>(R.id.todo_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }

    private fun getDataFormatted(day: Int, month: Int, year: Int): String{
        var date = ""
        if(day < 10){
            date += "0$day"
        }else{
            date += day
        }
        if(month < 10){
            date += "/0$month"
        }else{
            date +="/$month"
        }

        return date.plus("/$year")
    }
}