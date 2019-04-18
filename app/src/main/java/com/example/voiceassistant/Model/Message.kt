package com.example.voiceassistant.Model

import com.example.voiceassistant.Enums.MessageTypes
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.Model.Weather.CurrentWeather.CurrentWeather
import com.example.voiceassistant.Model.Weather.Weather

data class Message(val orderId: Int, val sender: Sender, val body: String, val date: String,
                   val type: MessageTypes = MessageTypes.MESSAGE, val weather: Weather? = null )