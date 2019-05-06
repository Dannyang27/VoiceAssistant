package com.example.voiceassistant.Adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class TodoListAdapter (private val todolist: MutableList<Task>): RecyclerView.Adapter<TodoListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_viewholder, parent, false)
        view.setOnClickListener {
            //TODO intent to todolist activity?
        }
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val task = todolist[position]
        holder.todolistText.text = task.text
        if(task.isDone){
            strikeText(holder)
        }
    }

    override fun getItemCount(): Int = todolist.size

    private fun strikeText( holder: TodoListViewHolder){
        holder.checkbox.isChecked = true
        holder.todolistText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

}