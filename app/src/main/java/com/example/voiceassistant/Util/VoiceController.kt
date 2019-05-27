package com.example.voiceassistant.Util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.TodoListFragment
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient

class VoiceController(ctx: Context){

    private val HELLO = listOf("HI", "HELLO", "SUP", "HEY")
    private val LOCAL_WEATHER = "TELL ME THE WEATHER"
    private val CITY_WEATHER = "TELL ME THE WEATHER IN"
    private val LOCAL_WEATHER_TOMORROW = "TELL ME THE WEATHER FOR TOMORROW"
    private val LOCAL_WEATHER_TOMORROW_CITY = "TELL ME THE WEATHER FOR TOMORROW IN"
    private val TEMPERATURE = "TELL ME THE TEMPERATURE"
    private val HUMIDITY = "TELL ME THE HUMIDITY"
    private val NEXT_DAYS = "TELL ME THE WEATHER FOR THE NEXT DAYS"
    private val NEXT_DAYS_CITY = "TELL ME THE WEATHER FOR THE NEXT DAYS IN"
    private val CALENDAR_TASK = "REMIND ME TO"
    private val TODOLIST = "NOTE"
    private val CURRENT_LOCATION = "WHERE AM I"
    private val GO_TO_TODO_LIST = "SHOW ME MY TASKS"


    private var googleSpeaker = GoogleSpeaker(ctx)
    val context = ctx

    fun processVoiceInput(voiceInput: String){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val name = prefs.getString("name", "Danny")
        val lastLocation = prefs.getString("last_location", "Barcelona")

        val editor = prefs.edit()
        editor.putString("lastQuery", voiceInput)
        editor.apply()

        VoiceAssistanceFragment.updateLastQuery(voiceInput)

        var isWeatherAbroad = false
        if(voiceInput.length> CITY_WEATHER.length){
            isWeatherAbroad = voiceInput.substring(0, CITY_WEATHER.length).toUpperCase() == CITY_WEATHER
        }

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

        var isNextDaysWeatherAbroad = false
        if(voiceInput.length > NEXT_DAYS_CITY.length){
            isNextDaysWeatherAbroad = voiceInput.substring(0, NEXT_DAYS_CITY.length).toUpperCase() == NEXT_DAYS_CITY
        }

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

        var isReminder = false
        if(voiceInput.length > CALENDAR_TASK.length){
            isReminder = voiceInput.substring(0, CALENDAR_TASK.length).toUpperCase() == CALENDAR_TASK
        }

        if(isReminder){
            val text = voiceInput.toUpperCase().split(" ")
            val reminder = CALENDAR_TASK.split(" ")

            if(text.size > reminder.size){
                val event = text.drop(3).joinToString(" ").toLowerCase().capitalize()
                CalendarUtils(context).createEventByIntent(event)
            }else{
                val response = "Sorry, could not get the reminder"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
            return
        }

        var isTomorrowWeatherCity = false
        if(voiceInput.length > LOCAL_WEATHER_TOMORROW_CITY.length){
            isTomorrowWeatherCity = voiceInput.substring(0, LOCAL_WEATHER_TOMORROW_CITY.length).toUpperCase() == LOCAL_WEATHER_TOMORROW_CITY
        }

        if(isTomorrowWeatherCity){
            val text = voiceInput.toUpperCase().split(" ")
            val tomorrowCity = LOCAL_WEATHER_TOMORROW_CITY.split(" ")

            if(text.size > tomorrowCity.size){
                val city = text.drop(tomorrowCity.size).joinToString(" ").toLowerCase().capitalize()
                RetrofitClient.getTomorrowWeather(city, voiceInput)
            }
            return
        }

        if(voiceInput.toUpperCase().split(" ")[0] == TODOLIST){
            val input = voiceInput.split(" ").drop(1).joinToString(" ").toLowerCase().capitalize()
            val epochs = TimeUtils.getTimeInEpochs()
            val task = TaskPOJO(epochs, 0, input)
            TaskRepository(context).insert(task)
            TodoListFragment.todolist.add(task)
            TodoListFragment.viewAdapter.notifyDataSetChanged()

            val response = "Sure, your note has been added to the to-do list"
            VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
            googleSpeaker.speak(response)

            return
        }


        when(voiceInput.toUpperCase()){
            in HELLO -> {
                val response = "Hi $name, how can I help you"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            TEMPERATURE -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput, "temperature")
            }

            HUMIDITY -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput, "humidity")
            }

            LOCAL_WEATHER -> {
                RetrofitClient.getWeatherByName(lastLocation, voiceInput)
            }

            LOCAL_WEATHER_TOMORROW -> {
                RetrofitClient.getTomorrowWeather(lastLocation, voiceInput)
            }

            NEXT_DAYS -> {
                RetrofitClient.getWeatherForecastByName(lastLocation, voiceInput)
            }

            CURRENT_LOCATION ->{
                val response = "Current city is $lastLocation"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }

            else -> {
                val response = "Sorry, I did not get it"
                VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                googleSpeaker.speak(response)
            }
        }
    }
}












