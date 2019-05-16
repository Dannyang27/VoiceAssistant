package com.example.voiceassistant.Adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class TodoListAdapter (private val todolist: MutableList<TaskPOJO>): RecyclerView.Adapter<TodoListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_viewholder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val task = todolist[position]
        holder.todolistText.text = task.text
        val context = holder.todolistText.context

        if(task.isDone == 1){
            strikeText(holder)
        }

        holder.itemView.setOnClickListener {
            if(task.isDone == 1){
                removeStrike(holder)
                task.isDone = 0
            }
            else{
                strikeText(holder)
                task.isDone = 1
            }
            TaskRepository(context).update(task)
        }
    }

    override fun getItemCount(): Int = todolist.size

    private fun strikeText( holder: TodoListViewHolder){
        holder.checkbox.isChecked = true
        holder.todolistText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun removeStrike( holder: TodoListViewHolder){
        holder.checkbox.isChecked = false
        holder.todolistText.paintFlags = Paint.ANTI_ALIAS_FLAG
    }
}