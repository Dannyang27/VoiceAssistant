package com.example.voiceassistant.Util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.NextWeather.City
import com.example.voiceassistant.Retrofit.RetrofitClient

class VoiceController(ctx: Context){

    private val HELLO = listOf("HI", "HELLO", "SUP")
    private val LOCAL_WEATHER = "TELL ME THE WEATHER"
    private val LOCAL_WEATHER_TOMORROW = "TELL ME THE WEATHER FOR TOMORROW"
    private val IS_IT_COLD_OUTSIDE = "IS IT COLD OUTSIDE"
    private val IS_IT_HOT_OUTSIDE = "IS IT HOT OUTSIDE"
    private val CITY_WEATHER = "TELL ME THE WEATHER IN"
    private val RAINING = listOf("SHOULD I GET AN UMBRELLA", "IS IT RAINING")
    private val TEMPERATURE = "TELL ME THE TEMPERATURE"
    private val HUMIDITY = "TELL ME THE HUMIDITY"
    private var googleSpeaker = GoogleSpeaker(ctx)


    fun processVoiceInput(voiceInput: String){
        val prefs = PreferenceManager.getDefaultSharedPreferences(VoiceAssistanceFragment.voiceContext)
        val name = prefs.getString("name", "N/A")
        val lastLocation = prefs.getString("last_location", "Barcelona")

        val text = voiceInput.toUpperCase().split(" ")
        val cityWeather = CITY_WEATHER.split(" ")

        val isWeatherAbroad = text.containsAll(cityWeather)
        if(isWeatherAbroad){
            if(text.size > cityWeather.size){
                Log.d(RetrofitClient.TAG, "text size ${text.size}")
                Log.d(RetrofitClient.TAG, "cityWeather size ${cityWeather.size}")
                val city = text.drop(5).joinToString(" ")
                Log.d(RetrofitClient.TAG, city)
                RetrofitClient.getWeatherByName(city)

            }else{
                val response = "Sorry, could not get the city"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }

            return
        }
        when(voiceInput.toUpperCase()){
            in HELLO -> {
                val response = "Hi $name, I missed you"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }
            IS_IT_COLD_OUTSIDE -> {
                val response = "Nah, you should be fine"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }
            IS_IT_HOT_OUTSIDE ->{
                val response = "hot as hell mate"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }

            TEMPERATURE -> {
                RetrofitClient.getWeatherByName(lastLocation, "temperature")
            }

            HUMIDITY -> {
                RetrofitClient.getWeatherByName(lastLocation, "humidity")
            }

            in RAINING -> {
                val response = "Its raining cats and dogos"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }

            LOCAL_WEATHER -> {
                RetrofitClient.getWeatherByName(lastLocation)
            }

            LOCAL_WEATHER_TOMORROW -> {
                val response = "Tomorrow it will rain, so get your best coat Danny"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }

            else -> {
                val response = "Sorry, I did not get it"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                googleSpeaker.speak(response)
            }
        }
    }
}












