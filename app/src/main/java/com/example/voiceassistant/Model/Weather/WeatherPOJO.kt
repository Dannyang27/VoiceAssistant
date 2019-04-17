package com.example.voiceassistant.Model.Weather

import com.example.voiceassistant.Enums.WeatherType

data class WeatherPOJO (val city: String, val temp: Double, val humidity: Double,
                        val clima: WeatherType, val date: String, val query: String){

    companion object{
        val TABLE_NAME = "WeatherHistorial"
        val COLUMN_CITY = "city"
        val COLUMN_TEMP = "temperature"
        val COLUMN_HUMIDITY = "humidity"
        val COLUMN_CLIMA = "clima"
        val COLUMN_DATE = "date"
        val COLUM_QUERY = "query"
    }
}