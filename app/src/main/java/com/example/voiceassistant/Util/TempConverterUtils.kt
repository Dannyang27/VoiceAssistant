package com.example.voiceassistant.Util

object TempConverterUtils{

    const val kelvinCelsius = 273.15

    fun convertKelvinToCelsius(kelvin : Double) = kelvin - kelvinCelsius
}