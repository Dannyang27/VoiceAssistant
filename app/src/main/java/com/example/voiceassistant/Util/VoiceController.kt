package com.example.voiceassistant.Util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Retrofit.RetrofitClient

class VoiceController(ctx: Context){

    private val HELLO = listOf("HI", "HELLO", "SUP", "HEY")
    private val LOCAL_WEATHER = "TELL ME THE WEATHER"
    private val CITY_WEATHER = "TELL ME THE WEATHER IN"
    private val LOCAL_WEATHER_TOMORROW = "TELL ME THE WEATHER FOR TOMORROW"
    private val IS_IT_COLD_OUTSIDE = "IS IT COLD OUTSIDE"
    private val IS_IT_HOT_OUTSIDE = "IS IT HOT OUTSIDE"
    private val RAINING = listOf("SHOULD I GET AN UMBRELLA", "IS IT RAINING")
    private val TEMPERATURE = "TELL ME THE TEMPERATURE"
    private val HUMIDITY = "TELL ME THE HUMIDITY"
    private val NEXT_DAYS = "TELL ME THE WEATHER FOR THE NEXT DAYS"
    private val NEXT_DAYS_CITY = "TELL ME THE WEATHER FOR THE NEXT DAYS IN"

    private var googleSpeaker = GoogleSpeaker(ctx)


    fun processVoiceInput(voiceInput: String){
        val prefs = PreferenceManager.getDefaultSharedPreferences(VoiceAssistanceFragment.voiceContext)
        val name = prefs.getString("name", "N/A")
        val lastLocation = prefs.getString("last_location", "Barcelona")

        var isWeatherAbroad = false
        if(voiceInput.length> CITY_WEATHER.length){
            isWeatherAbroad = voiceInput.substring(0, CITY_WEATHER.length).toUpperCase() == CITY_WEATHER
        }
        Log.d(RetrofitClient.TAG, "" + isWeatherAbroad)
        if(isWeatherAbroad){
            val text = voiceInput.toUpperCase().split(" ")
            val cityWeather = CITY_WEATHER.split(" ")

            if(text.size > cityWeather.size){
                val city = text.drop(5).joinToString(" ").toLowerCase().capitalize()
                RetrofitClient.getWeatherByName(city, voiceInput)

            }else{
                val response = "Sorry, could not get the city"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            return
        }


//        val isNextDaysWeatherAbroad = voiceInput.substring(0, NEXT_DAYS_CITY.length).toUpperCase().or == NEXT_DAYS_CITY

        var isNextDaysWeatherAbroad = false
        if(voiceInput.length > NEXT_DAYS_CITY.length){
            isNextDaysWeatherAbroad = voiceInput.substring(0, NEXT_DAYS_CITY.length).toUpperCase() == NEXT_DAYS_CITY
        }

        Log.d(RetrofitClient.TAG, "isnextdayweatherabroad: " + isNextDaysWeatherAbroad)


        if(isNextDaysWeatherAbroad){
            val text = voiceInput.toUpperCase().split(" ")
            val nextDaysWeather = NEXT_DAYS_CITY.split(" ")

            if(text.size > nextDaysWeather.size){
                val city = text.drop(9).joinToString(" ").toLowerCase().capitalize()
                RetrofitClient.getWeatherForecastByName(city, voiceInput)
            }else{
                val response = "Sorry, could not get the city"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
            return
        }

        when(voiceInput.toUpperCase()){
            in HELLO -> {
                val response = "Hi $name, I missed you"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
            IS_IT_COLD_OUTSIDE -> {
                val response = "Nah, you should be fine"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
            IS_IT_HOT_OUTSIDE ->{
                val response = "Hot as hell mate"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            TEMPERATURE -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput, "temperature")
            }

            HUMIDITY -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput, "humidity")
            }

            in RAINING -> {
                val response = "Its raining cats and dogos"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            LOCAL_WEATHER -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput)
            }

            LOCAL_WEATHER_TOMORROW -> {
                val response = "Tomorrow it will rain, so get your best coat Danny"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            NEXT_DAYS -> {
                RetrofitClient.getWeatherForecastByName(lastLocation, voiceInput)
            }

            else -> {
                val response = "Sorry, I did not get it"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
        }
    }
}












