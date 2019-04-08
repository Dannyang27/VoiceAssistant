package com.example.voiceassistant.Retrofit

import com.example.voiceassistant.Model.Weather.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/data/2.5/weather")
    fun getWeatherByCityName( @Query("q") location: String, @Query("appid") apiKey: String) : Call<Weather>

    @GET("/data/2.5/weather")
    fun getWeatherByCityId( @Query("id") cityId: String, @Query("appid") apiKey: String) : Call<Weather>
}