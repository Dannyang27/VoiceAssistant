package com.example.voiceassistant.Util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {

    fun getCurrentTime(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
    }

    fun getTodayDate(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return current.format(formatter)
    }
}