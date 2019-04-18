package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.voiceassistant.R

class WeatherHistorialViewHolder(view: View): RecyclerView.ViewHolder(view){
    val image = view.findViewById<ImageView>(R.id.weather_historial_pic)
    val clima = view.findViewById<TextView>(R.id.clima_historial_text)
    val dayOfWeek = view.findViewById<TextView>(R.id.dayOfWeek_historial)
    val city = view.findViewById<TextView>(R.id.weather_historial_city)
    val temperature = view.findViewById<TextView>(R.id.weather_historial_temp)
    val humidity = view.findViewById<TextView>(R.id.weather_historial_humidity)
    val date = view.findViewById<TextView>(R.id.weather_historial_time)
    val query = view.findViewById<TextView>(R.id.weather_historial_query)
}