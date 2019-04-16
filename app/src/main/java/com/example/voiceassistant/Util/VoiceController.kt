package com.example.voiceassistant.Util

import android.preference.PreferenceManager
import android.util.Log
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Retrofit.RetrofitClient

object VoiceController{

    private val HELLO = listOf("HI", "HELLO", "SUP")
    private const val LOCAL_WEATHER = "TELL ME THE WEATHER"
    private const val LOCAL_WEATHER_TOMORROW = "TELL ME THE WEATHER FOR TOMORROW"
    private const val IS_IT_COLD_OUTSIDE = "IS IT COLD OUTSIDE"
    private const val IS_IT_HOT_OUTSIDE = "IS IT HOT OUTSIDE"
    private const val CITY_WEATHER = "TELL WE THE WEATHER IN"
    private val RAINING = listOf("SHOULD I GET AN UMBRELLA", "IS IT RAINING")

    fun processVoiceInput(voiceInput: String): String{

        var response = ""

        if( voiceInput.toUpperCase() in CITY_WEATHER){
            Log.d(RetrofitClient.TAG, "SPLIT:" +  voiceInput.split(" ").toString())
            val city = voiceInput.split(" ").get(5)
            RetrofitClient.getWeatherByName(city, "temperature")

            return response

        }
        when(voiceInput.toUpperCase()){
            in HELLO -> {
                val prefs = PreferenceManager.getDefaultSharedPreferences(VoiceAssistanceFragment.voiceContext)
                val name = prefs.getString("name", "N/A")
                response = "Hi $name, I missed you"
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

        return response
    }
}












