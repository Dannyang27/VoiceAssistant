package com.example.voiceassistant.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.voiceassistant.Adapter.TaskAdapter
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.HorizontalDivider

class TodoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        val toolbar = findViewById<Toolbar>(R.id.task_toolbar)
        val date = intent.getStringExtra("date")
        toolbar.title = date
        val taskList = TaskRepository(this).findTaskByDate(date)
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = TaskAdapter(taskList)

        val recyclerView = findViewById<RecyclerView>(R.id.task_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }
    }
}
