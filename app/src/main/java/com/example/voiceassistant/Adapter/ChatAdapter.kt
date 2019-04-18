package com.example.voiceassistant.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Enums.MessageTypes
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.CurrentWeather.CurrentWeather
import com.example.voiceassistant.Model.Weather.NextWeather.Forecast
import com.example.voiceassistant.Model.Weather.NextWeather.Weather
import com.example.voiceassistant.Model.Weather.NextWeather.WeatherList
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.TempConverterUtils
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Util.WeatherUtils
import com.example.voiceassistant.Viewholder.BotMessageViewHolder
import com.example.voiceassistant.Viewholder.ForecastViewHolder
import com.example.voiceassistant.Viewholder.UserMessageViewHolder
import com.example.voiceassistant.Viewholder.WeatherCardViewHolder
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatAdapter( val messages: MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        var viewType: Int

        if(messages[position].type == MessageTypes.WEATHER_CARD){
            return 2
        }

        if(messages[position].type == MessageTypes.FORECAST){
            return 2
        }

        if(messages[position].sender == Sender.USER){
            viewType = 0
        }else{
            viewType = 1
        }

        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view :View? = null
        when(viewType){
            0 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_message, parent, false)
                return UserMessageViewHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.bot_message, parent, false)
                return BotMessageViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.weather_viewholder, parent, false)
                return WeatherCardViewHolder(view)
            }
        }
        // TODO dont know what to return, default one
        return UserMessageViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when(holder){
            is UserMessageViewHolder -> {
                holder.userText?.text = message.body
            }

            is BotMessageViewHolder -> {
                holder.botText?.text = message.body
            }

            is WeatherCardViewHolder ->{
                val weather = message.weather
                val clima = WeatherUtils.getWeatherCondition(weather?.clima!!)
                val image = WeatherUtils.getImageByWeather(clima)
                Picasso.with(holder.weatherPic.context)
                    .load(image)
                    .placeholder(image)
                    .into(holder.weatherPic)

                holder.climaText.text = clima
                holder.dayOfWeek.text = weather.dayOfWeek
                holder.city.text = weather.city
                holder.temperature.text = "${weather.temp}°"
                holder.humidity.text = "${weather.humidity}%"
                holder.time.text = message.date
            }
        }
    }


    override fun getItemCount() = messages.size
}