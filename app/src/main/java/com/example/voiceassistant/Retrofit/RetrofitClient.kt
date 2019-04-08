package com.example.voiceassistant.Retrofit

import android.util.Log
import com.example.voiceassistant.Model.Weather.Weather
import com.example.voiceassistant.Util.TempConverterUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    const val token = "b4139617dc083e665e03aa3f9d1d0064"
    const val baseUrl = "https://api.openweathermap.org"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GithubService::class.java)

    fun getWeatherByName( city: String){
        val call = service.getWeatherByCityName(city, token)

        call.enqueue(object : Callback<Weather>{

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val weather: Weather? = response.body()
                weather.let {
                    val currentTemp ="%.1f".format(TempConverterUtils.convertKelvinToCelsius(it?.main?.temp!!))
                    Log.d("Weather of $city", "$currentTemp")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Weather", "Could not get weather for city $city")
            }
        })
    }

    fun getWeatherById( cityId: Int){
        val call = service.getWeatherByCityId(cityId.toString(), token)

        call.enqueue(object : Callback<Weather>{

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val weather: Weather? = response.body()
                weather.let {
                    val currentTemp ="%.1f".format(TempConverterUtils.convertKelvinToCelsius(it?.main?.temp!!))
                    Log.d("Weather of $cityId", "$currentTemp")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Weather", "Could not get weather for city $cityId")
            }
        })
    }
}