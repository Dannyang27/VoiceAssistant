package com.example.voiceassistant.Util

import com.example.voiceassistant.R

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

    fun getImageByWeather(weather: String): Int{
        var image = -1
        when(weather){
            "Rain" -> {
                image = R.drawable.rainy
            }
            "Clouds" -> {
                image = R.drawable.cloudy
            }
            "Sunny" -> {
                image = R.drawable.sunny
            }
            "Snow" -> {
                image = R.drawable.snowy
            }
        }

        return image
    }
}