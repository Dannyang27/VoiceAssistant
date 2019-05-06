package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.example.voiceassistant.R

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val checkbox = view.findViewById(R.id.task_checkbox) as CheckBox
    val todolistText = view.findViewById(R.id.task_text) as TextView
}