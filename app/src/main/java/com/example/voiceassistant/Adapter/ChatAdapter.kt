package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.BotMessageViewHolder
import com.example.voiceassistant.Viewholder.UserMessageViewHolder

class ChatAdapter( val messages: MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        var viewType: Int
        if(messages[position].sender == Sender.USER){
            viewType = 0
        }else{
            viewType = 1
        }

        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view :View? = null
        when(viewType){
            0 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_message, parent, false)
                return UserMessageViewHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.bot_message, parent, false)
                return BotMessageViewHolder(view)
            }
        }
        // TODO dont know what to return, default one
        return UserMessageViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if(holder is UserMessageViewHolder){
            holder.userText?.text = message.body
        }else if(holder is BotMessageViewHolder){
            holder.botText?.text = message.body
        }
    }

    override fun getItemCount() = messages.size
}