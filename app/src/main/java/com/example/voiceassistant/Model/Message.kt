package com.example.voiceassistant.Model

import com.example.voiceassistant.Enums.MessageTypes
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.Model.Weather.CurrentWeather.CurrentWeather

data class Message(val orderId: Int, val sender: Sender, val body: String,
                   val type: MessageTypes = MessageTypes.MESSAGE, val weather: CurrentWeather? = null )