package com.example.voiceassistant.Viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.voiceassistant.R

class ForecastViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val weatherPic = view.findViewById<ImageView>(R.id.weather_pic_forecast)
    val climaText = view.findViewById<TextView>(R.id.clima_text_forecast)
    val city = view.findViewById<TextView>(R.id.weather_cardview_city_forecast)
    val temperature = view.findViewById<TextView>(R.id.weather_cardview_temp_forecast)
    val humidity = view.findViewById<TextView>(R.id.weather_cardview_humidity_forecast)
    val time = view.findViewById<TextView>(R.id.weather_cardview_time_forecast)
}