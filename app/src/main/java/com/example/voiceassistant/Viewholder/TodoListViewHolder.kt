package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.example.voiceassistant.R

class TodoListViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var id = ""
    val checkbox = view.findViewById(R.id.todolist_checkbox) as CheckBox
    val todolistText = view.findViewById(R.id.todolist_textview) as TextView
}