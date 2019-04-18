package com.example.voiceassistant.Retrofit

import android.util.Log
import com.example.voiceassistant.Database.WeatherRepository
import com.example.voiceassistant.Enums.MessageTypes
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.MainActivityFragment.WeatherHistoryFragment
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.CurrentWeather.CurrentWeather
import com.example.voiceassistant.Model.Weather.NextWeather.Forecast
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.Util.TempConverterUtils
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Util.WeatherUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object RetrofitClient{
    const val token = "b4139617dc083e665e03aa3f9d1d0064"
    const val baseUrl = "https://api.openweathermap.org"
    const val TAG = "Weather"
    val googleSpeaker = GoogleSpeaker(VoiceAssistanceFragment.voiceContext)
//    http://api.openweathermap.org/data/2.5/forecast?q=London&appid=b4139617dc083e665e03aa3f9d1d0064
//    https://api.openweathermap.org/data/2.5/forecast?q=london&appid=b4139617dc083e665e03aa3f9d1d0064

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GithubService::class.java)

    fun getWeatherByName( city: String, query: String, property: String = "all"){
        val call = service.getWeatherByCityName(city, token)

        call.enqueue(object : Callback<CurrentWeather>{

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val currentWeather: CurrentWeather? = response.body()
                currentWeather?.let {
                    var response : String
                    val currentTemp ="%.1f".format(TempConverterUtils.convertKelvinToCelsius(it.main.temp))
                    val humidity = it.main.humidity
                    try{
                        when(property){
                            "all" -> {
                                response = "Sure, the temperature is $currentTemp celsius and $humidity % of humidity"
                            }
                            "temperature" -> {
                                response = "The temperature in $city is $currentTemp celsius"
                            }
                            "humidity" -> {
                                response = "The humidity in $city is $humidity %"

                            }
                            "pressure" -> {
                                response = "The temperature in $city is ${it.main.pressure} pascal"
                            }
                            else -> response = "Sorry, I did not get it"
                        }
                    }catch (e : Exception){
                        response = "Sorry, could not get the city"
                        VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))
                        googleSpeaker.speak(response)
                    }

                    googleSpeaker.speak(response)

                    VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response, TimeUtils.getCurrentTime()))

                    if( property == "all"){
                        VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, "",
                            TimeUtils.getCurrentTime(), MessageTypes.WEATHER_CARD, currentWeather))

                        val weatherPojo = WeatherPOJO(city, currentTemp.toDouble(), humidity.toDouble(),
                            WeatherUtils.getWeatherCondition(currentWeather.weather[0].main), TimeUtils.getCurrentTime(), query)

                        WeatherRepository(VoiceAssistanceFragment.voiceContext).insert(weatherPojo)
                        WeatherHistoryFragment.addWeather(weatherPojo)
                        Log.d(TAG, "Inserted $weatherPojo")
                    }
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d(TAG, "Could not get weather for city $city")
            }
        })
    }

    fun getWeatherById( cityId: Int){
        val call = service.getWeatherByCityId(cityId.toString(), token)

        call.enqueue(object : Callback<CurrentWeather>{

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val currentWeather: CurrentWeather? = response.body()
                currentWeather.let {
                    val currentTemp ="%.1f".format(TempConverterUtils.convertKelvinToCelsius(it?.main?.temp!!))
                    Log.d(TAG, "$currentTemp")
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d(TAG, "Could not get weather for city $cityId")
            }
        })
    }


    fun getWeatherForecastByName(city: String){
        val call = service.getForecastByCityName(city, token)
        Log.d(TAG, "Test forecast for city $city")

        call.enqueue(object: Callback<Forecast>{

            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                Log.d(TAG, "Retrieving forecast")
                val forecast = response.body()
                forecast?.let {
                    it.list.forEach {

                    }
                }

            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d(TAG, "Could not get forecast")
            }
        })
    }
}