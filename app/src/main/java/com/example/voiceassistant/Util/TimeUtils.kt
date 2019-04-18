package com.example.voiceassistant.Util

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    fun getDayOfWeek(daysToincrement : Int): String{

        if(daysToincrement == 0){
            return "Now"
        }

        if(daysToincrement == 1){
            return "Tomorrow"
        }

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, daysToincrement)
        var day = calendar[Calendar.DAY_OF_WEEK]

        when(day){
            Calendar.SUNDAY -> {
                return "Sunday"
            }
            Calendar.MONDAY -> {
                return "Monday"
            }
            Calendar.TUESDAY -> {
                return "Tuesday"
            }
            Calendar.WEDNESDAY -> {
                return "Wednesday"
            }
            Calendar.THURSDAY -> {
                return "Thursday"
            }
            Calendar.FRIDAY -> {
                return "Friday"
            }
            Calendar.SATURDAY -> {
                return "Saturday"
            }
        }

        return "Now"
    }
}