package com.example.voiceassistant.Model

import com.example.voiceassistant.Enums.Sender

data class Message(val orderId: Int, val sender: Sender, val body: String )