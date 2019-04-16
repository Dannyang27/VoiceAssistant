package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Viewholder.WeatherHistorialViewHolder

class HistorialAdapter(val historial: MutableList<WeatherPOJO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_historial_viewholder, parent, false)
        return WeatherHistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = historial.size
}