package com.example.voiceassistant.Util

object WeatherUtils{

    val rainList = mutableListOf("Drizzle", "Rain")
    val cloudList = mutableListOf("Clouds", "Mist", "Smoke", "Haze", "Dust", "Fog", "Sand", "Squall", "Tornado", "Thunderstorm")
    val sunnyList = mutableListOf("Sunny", "Clear")
    val snowList = mutableListOf("Snow")

    fun getWeatherCondition(condition: String): String{
        var weather = "N/A"
        when(condition){
            in rainList -> {
                weather = "Rain"
            }

            in cloudList -> {
                weather = "Clouds"
            }

            in sunnyList -> {
                weather = "Sunny"
            }

            in snowList -> {
                weather = "Snow"
            }
        }

        return weather
    }
}