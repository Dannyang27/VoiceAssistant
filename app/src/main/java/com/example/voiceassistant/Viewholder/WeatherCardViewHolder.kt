package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.voiceassistant.R

class WeatherCardViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val weatherPic = view.findViewById<ImageView>(R.id.weather_pic)
    val climaText = view.findViewById<TextView>(R.id.clima_text)
    val city = view.findViewById<TextView>(R.id.weather_cardview_city)
    val temperature = view.findViewById<TextView>(R.id.weather_cardview_temp)
}