package com.example.voiceassistant.Retrofit

import android.util.Log
import android.widget.Toast
import com.example.voiceassistant.Model.Weather.Main
import com.example.voiceassistant.Model.Weather.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    const val token = "b4139617dc083e665e03aa3f9d1d0064"
    const val baseUrl = "https://openweathermap.org"
    val unitType = "imperial"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GithubService::class.java)

    fun getWeather( city: String){
        val call = service.getWeatherFromApi(city, token, unitType)

        call.enqueue(object : Callback<Weather>{

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val weather: Main? = response.body()?.main
                weather.let {
                    Log.d("Weather", "${it?.temp}")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Weather", "Could not get weather for city $city")
            }
        })
    }
}