package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.voiceassistant.R

class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val userText = view.findViewById(R.id.user_message_body) as? TextView
}