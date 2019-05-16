package com.example.voiceassistant.Adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.TaskViewHolder

class TaskAdapter (private val todolist: MutableList<TaskPOJO>): RecyclerView.Adapter<TaskViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_viewholder, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = todolist[position]
        holder.todolistText.text = task.text

        if(task.isDone == 1){
            strikeText(holder)
        }

        holder.itemView.setOnClickListener {
            if(task.isDone == 0){
                removeStrike(holder)
                task.isDone = 1
            }
            else{
                strikeText(holder)
                task.isDone = 0
            }
        }
    }

    override fun getItemCount(): Int = todolist.size

    private fun strikeText( holder: TaskViewHolder){
        holder.checkbox.isChecked = true
        holder.todolistText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun removeStrike( holder: TaskViewHolder){
        holder.checkbox.isChecked = false
        holder.todolistText.paintFlags = Paint.ANTI_ALIAS_FLAG
    }
}