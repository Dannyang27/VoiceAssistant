package com.example.voiceassistant.Adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class TodoListAdapter (private val todolist: MutableList<Task>): RecyclerView.Adapter<TodoListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_viewholder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val task = todolist[position]
        holder.todolistText.text = task.text

        if(task.isDone){
            strikeText(holder)
        }

        holder.checkbox.setOnCheckedChangeListener { _ , isChecked ->
            if(isChecked){
                removeStrike(holder)
            }else{
                strikeText(holder)
            }
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