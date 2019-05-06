package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.TodoListViewHolder

class TodoListAdapter (val todolist: MutableList<String>): RecyclerView.Adapter<TodoListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_viewholder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.todolistText.text = todolist.get(position)
    }

    override fun getItemCount(): Int = todolist.size
}