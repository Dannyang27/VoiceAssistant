package com.example.voiceassistant.Model.Weather.CurrentWeather

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)