package com.example.voiceassistant.MainActivityFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.voiceassistant.Activity.TodoListActivity
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Viewholder.HorizontalDivider
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class CalendarFragment : Fragment(){

    lateinit var viewManager : RecyclerView.LayoutManager
    lateinit var todolistRecyclerView: RecyclerView
    lateinit var viewAdapter : RecyclerView.Adapter<TodoListViewHolder>

    var date = TimeUtils.getTodayDate()
    var todolist = mutableListOf<TaskPOJO>()

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment , container, false)
        val calendar = view.findViewById<CalendarView>(R.id.calendar)
        val todolistTitle = view.findViewById<TextView>(R.id.todolist_date)
        todolistTitle.text = date

        val viewAllBtn = view.findViewById<Button>(R.id.view_all_button)

        todolist = TaskRepository(activity?.applicationContext!!).findTaskByDate(date)

        calendar.setOnDateChangeListener{ _ , year, month, dayOfMonth ->
            date = getDataFormatted(dayOfMonth, month + 1, year)
            todolistTitle.text = date
            todolist.clear()
            todolist.addAll(TaskRepository(activity?.applicationContext!!).findTaskByDate(date))
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