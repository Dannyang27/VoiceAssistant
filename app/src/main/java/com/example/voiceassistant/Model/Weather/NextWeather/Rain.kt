package com.example.voiceassistant.Model.Weather.NextWeather

import com.google.gson.annotations.SerializedName

data class Rain (

	@SerializedName("3h") val hour : Double
)