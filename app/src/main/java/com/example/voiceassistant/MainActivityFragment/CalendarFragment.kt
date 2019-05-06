package com.example.voiceassistant.MainActivityFragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.voiceassistant.Activity.TodoListActivity
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Viewholder.HorizontalDivider
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class CalendarFragment : Fragment(){

    lateinit var viewManager : RecyclerView.LayoutManager
    lateinit var todolistRecyclerView: RecyclerView
    lateinit var viewAdapter : RecyclerView.Adapter<TodoListViewHolder>

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calendar_fragment , container, false)
        val calendar = view.findViewById<CalendarView>(R.id.calendar)
        val todolistTitle = view.findViewById<TextView>(R.id.todolist_date)
        todolistTitle.text = TimeUtils.getTodayDate()

        val viewAllBtn = view.findViewById<Button>(R.id.view_all_button)

        calendar.setOnDateChangeListener{view, year, month, dayOfMonth ->
            todolistTitle.text = "$dayOfMonth/${month + 1}/$year"
        }

        viewAllBtn.setOnClickListener {
            val intent = Intent(activity, TodoListActivity::class.java)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TodoListAdapter(mutableListOf(
            Task(true, "Pasear al perro"),
            Task(false, "Hacer la comida al niño"),
            Task(true, "Enviar email a Pepe"),
            Task(false, "Ir al gimnasio"),
            Task(false, "Ducharse")))

        todolistRecyclerView = view.findViewById<RecyclerView>(R.id.todo_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }


        return view
    }
}