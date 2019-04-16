package com.example.voiceassistant.Database

import android.content.Context
import android.util.Log
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import org.jetbrains.anko.db.*

class WeatherRepository(val context: Context){

    fun findAll(): MutableList<WeatherPOJO> = context.database.use {

        val weathers = mutableListOf<WeatherPOJO>()

        select(WeatherPOJO.TABLE_NAME, WeatherPOJO.COLUMN_CITY, WeatherPOJO.COLUMN_TEMP,
            WeatherPOJO.COLUMN_HUMIDITY, WeatherPOJO.COLUMN_CLIMA, WeatherPOJO.COLUMN_DATE, WeatherPOJO.COLUMN_IMAGE,
            WeatherPOJO.COLUM_QUERY)
            .parseList(object: MapRowParser<MutableList<WeatherPOJO>>{
                override fun parseRow(columns: Map<String, Any?>): MutableList<WeatherPOJO> {
                    val city = columns.getValue(WeatherPOJO.COLUMN_CITY)
                    val temp = columns.getValue(WeatherPOJO.COLUMN_TEMP)
                    val humidity = columns.getValue(WeatherPOJO.COLUMN_HUMIDITY)
                    val clima = columns.getValue(WeatherPOJO.COLUMN_CLIMA)
                    val date = columns.getValue(WeatherPOJO.COLUMN_DATE)
                    val image = columns.getValue(WeatherPOJO.COLUMN_IMAGE)
                    val query = columns.getValue(WeatherPOJO.COLUM_QUERY)

                    val weather = WeatherPOJO(city.toString(), temp.toString().toDouble(),
                        humidity.toString().toDouble(), clima.toString(), date.toString(), image.toString(), query.toString())

                    weathers.add(weather)
                    return weathers
                }
            })

        weathers
    }

    fun insert(weather: WeatherPOJO) = context.database.use{
        insert(WeatherPOJO.TABLE_NAME,
            WeatherPOJO.COLUMN_CITY to weather.city,
            WeatherPOJO.COLUMN_TEMP to weather.temp,
            WeatherPOJO.COLUMN_HUMIDITY to weather.humidity,
            WeatherPOJO.COLUMN_CLIMA to weather.clima,
            WeatherPOJO.COLUMN_DATE to weather.date,
            WeatherPOJO.COLUMN_IMAGE to weather.image,
            WeatherPOJO.COLUM_QUERY to weather.query)
    }


    fun update(weather: WeatherPOJO) = context.database.use {
        val updateResult = update(WeatherPOJO.TABLE_NAME,
            WeatherPOJO.COLUMN_CITY to weather.city,
            WeatherPOJO.COLUMN_TEMP to weather.temp,
            WeatherPOJO.COLUMN_HUMIDITY to weather.humidity,
            WeatherPOJO.COLUMN_CLIMA to weather.clima,
            WeatherPOJO.COLUMN_DATE to weather.date,
            WeatherPOJO.COLUMN_IMAGE to weather.image,
            WeatherPOJO.COLUM_QUERY to weather.query)
            .whereArgs("${WeatherPOJO.COLUMN_CITY} = {${weather.city}", WeatherPOJO.COLUMN_CITY to weather.city)
            .exec()

        Log.d(RetrofitClient.TAG, "Update result code is $updateResult")
    }

    fun deleteByCity(city : String) = context.database.use {
         delete(WeatherPOJO.TABLE_NAME,  "city={city}", "city" to city)
    }
}

