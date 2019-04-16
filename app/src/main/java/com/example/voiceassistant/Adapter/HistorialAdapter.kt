package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.WeatherHistorialViewHolder

class HistorialAdapter(val historial: MutableList<WeatherPOJO>) : RecyclerView.Adapter<WeatherHistorialViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_historial_viewholder, parent, false)
        return WeatherHistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHistorialViewHolder, position: Int) {
        val weather = historial[position]

        holder.query.text = weather.query
        holder.city.text = weather.city
        holder.temperature.text = weather.temp.toString()
        holder.humidity.text = weather.humidity.toString()
        holder.date.text = weather.date
    }

    override fun getItemCount(): Int = historial.size
}