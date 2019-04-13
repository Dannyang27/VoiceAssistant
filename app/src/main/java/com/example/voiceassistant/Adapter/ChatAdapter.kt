package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.UserMessageViewHolder

class ChatAdapter( val messages: MutableList<Message>) : RecyclerView.Adapter<UserMessageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_message, parent, false)
        return UserMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserMessageViewHolder, position: Int) {
        holder.userText.text = messages[position].body
    }

    override fun getItemCount() = messages.size
}