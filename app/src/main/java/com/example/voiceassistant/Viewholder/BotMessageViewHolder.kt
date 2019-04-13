package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.voiceassistant.R

class BotMessageViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val botText = view.findViewById(R.id.bot_message_body) as? TextView
}