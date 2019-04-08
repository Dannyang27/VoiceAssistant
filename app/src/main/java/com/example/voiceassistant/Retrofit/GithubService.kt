package com.example.voiceassistant.Retrofit

import com.example.voiceassistant.Model.Weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/data/2.5/weather")
    fun getWeatherFromApi( @Query("q") location: String, @Query("appid") apiKey: String) : Call<Weather>
}