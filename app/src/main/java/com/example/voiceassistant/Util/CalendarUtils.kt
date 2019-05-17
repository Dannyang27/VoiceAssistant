package com.example.voiceassistant.Util

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import java.util.*

class CalendarUtils(ctx: Context) {
    val context = ctx

    fun createEventByIntent( description : String){
        val today = TimeUtils.getTodayDate().split("/")
        val day = today[0].toInt()
        val month = today[1].toInt()
        val year = today[2].toInt()

        val beginTime = Calendar.getInstance()
        beginTime.set(year, month, day,8,30)
        val endTime = Calendar.getInstance()
        endTime.set(year, month, day,23,30)

        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            .putExtra(CalendarContract.Events.TITLE, description)
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)
    }
}