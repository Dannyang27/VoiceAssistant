package com.example.voiceassistant.Util

import android.util.Log
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Retrofit.RetrofitClient

object VoiceController{

    val HELLO = listOf("HI", "HELLO", "SUP")
    const val LOCAL_WEATHER = "TELL ME THE WEATHER"
    const val LOCAL_WEATHER_TOMORROW = "TELL ME THE WEATHER FOR TOMORROW"
    const val IS_IT_COLD_OUTSIDE = "IS IT COLD OUTSIDE"
    const val IS_IT_HOT_OUTSIDE = "IS IT HOT OUTSIDE"
    val RAINING = listOf("SHOULD I GET AN UMBRELLA", "IS IT RAINING")

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
//        VoiceAssistanceFragment.messages.add(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, "Sure!"))
//        VoiceAssistanceFragment.viewAdapter.notifyDataSetChanged()

        var response = ""
        when(voiceInput.toUpperCase()){
            in HELLO -> {
                response = "Hi Danny, I missed you"
            }
            IS_IT_COLD_OUTSIDE -> {
                response = "Nah, you should be fine"
            }
            IS_IT_HOT_OUTSIDE ->{
                response = "hot as hell mate"
            }

            in RAINING -> {
                response = "Its raining cats and dogos"
            }

            LOCAL_WEATHER -> {
                response = "Current temperature is 16 Celsius"
            }

            LOCAL_WEATHER_TOMORROW -> {
                response = "Tomorrow it will rain, so get your best coat Danny"
            }

            else -> response = "Sorry, I did not get it"

        }
        VoiceAssistanceFragment.messages.add(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))

        return response
    }
}












