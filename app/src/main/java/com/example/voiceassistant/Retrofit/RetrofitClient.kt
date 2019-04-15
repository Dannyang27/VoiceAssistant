package com.example.voiceassistant.Retrofit

import android.util.Log
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivityFragment.VoiceAssistanceFragment
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.CurrentWeather.CurrentWeather
import com.example.voiceassistant.Util.TempConverterUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    const val token = "b4139617dc083e665e03aa3f9d1d0064"
    const val baseUrl = "https://api.openweathermap.org"
    const val TAG = "Weather"
//    http://api.openweathermap.org/data/2.5/forecast?q=London&appid=b4139617dc083e665e03aa3f9d1d0064
//    https://api.openweathermap.org/data/2.5/forecast?q=london&appid=b4139617dc083e665e03aa3f9d1d0064

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GithubService::class.java)

    fun getWeatherByName( city: String, property: String = ""){
        val call = service.getWeatherByCityName(city, token)

        call.enqueue(object : Callback<CurrentWeather>{

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val currentWeather: CurrentWeather? = response.body()
                currentWeather.let {
                    val currentTemp ="%.1f".format(TempConverterUtils.convertKelvinToCelsius(it?.main?.temp!!))
                    Log.d(TAG, "$currentTemp")

                    var response = ""
                    when(property){
                        "temperature" -> {
                            val celsius  = "%.1f".format(TempConverterUtils.convertKelvinToCelsius(it.main.temp))
                            response = "The temperature in $city is $celsius celsius"
                        }
                        "humidity" -> {
                            response = "The temperature in $city is ${it.main.humidity}%"

                        }
                        "pressure" -> {
                            response = "The temperature in $city is ${it.main.pressure} pascal"
                        }
                        else -> response = "Sorry, I did not get it"
                    }

                    VoiceAssistanceFragment.addMessage(Message(VoiceAssistanceFragment.messages.size, Sender.BOT, response))
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d(TAG, "Could not get weather for city $city")
            }
        })
    }

    fun getWeatherByNameSync( city: String ){
        val call = service.getWeatherByCityName(city, token)

        //val temp = call.execute().body()?.main?.temp
        //Log.d(TAG, "Temp: $temp")
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
}