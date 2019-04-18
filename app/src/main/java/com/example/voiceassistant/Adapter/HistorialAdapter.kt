package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.WeatherUtils
import com.example.voiceassistant.Viewholder.WeatherHistorialViewHolder
import com.squareup.picasso.Picasso

class HistorialAdapter(private val historial: MutableList<WeatherPOJO>) : RecyclerView.Adapter<WeatherHistorialViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_historial_viewholder, parent, false)
        return WeatherHistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHistorialViewHolder, position: Int) {
        val weather = historial[position]
        val clima = WeatherUtils.getWeatherCondition(weather.clima)

        val image = WeatherUtils.getImageByWeather(clima)
        Picasso.with(holder.image.context)
            .load(image)
            .placeholder(image)
            .into(holder.image)

        holder.clima.text = clima
        holder.query.text = weather.query
        holder.dayOfWeek.text = weather.dayOfWeek
        holder.city.text = weather.city
        holder.temperature.text = "${weather.temp}°"
        holder.humidity.text = "${weather.humidity}%"
        holder.date.text = weather.date
    }

    override fun getItemCount(): Int = historial.size
}