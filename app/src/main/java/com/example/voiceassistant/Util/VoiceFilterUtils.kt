package com.example.voiceassistant.Util

import android.util.Log
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Retrofit.RetrofitClient

object VoiceController{

    val cityHashmap = mapOf(
        Pair("Barcelona", 6356055),
        Pair("Londres", 6058560),
        Pair("Denia", 2518878),
        Pair("Valencia", 2509954),
        Pair("Madrid", 6359304),
        Pair("Amsterdam", 2759794),
        Pair("Tokio", 1850147),
        Pair("Paris", 6455259),
        Pair("Berlin", 2950158)
    )

    fun getIdByCity(words: List<String>) : Int{
    Log.d(RetrofitClient.TAG, words.toString())
        for (word in words){
            for(key in cityHashmap.keys){
                if(key == word){
                    return cityHashmap[key]!!
                }
            }
        }

        return -1
    }

    fun processVoiceInput(voiceInput: String): String{
        // Create string and add to VoiceAssistaceFragment.Adapter
        VoiceAssistanceFragment.messages.add(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, "Sure!"))
//        VoiceAssistanceFragment.viewAdapter.notifyDataSetChanged()
        return "Sure"
    }
}