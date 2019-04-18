package com.example.voiceassistant.Model.Weather.NextWeather

import com.google.gson.annotations.SerializedName

data class Forecast (

    @SerializedName("cod") val cod : Int,
    @SerializedName("message") val message : Double,
    @SerializedName("cnt") val cnt : Int,
    @SerializedName("list") val list : List<WeatherList>,
    @SerializedName("city") val city : City
)