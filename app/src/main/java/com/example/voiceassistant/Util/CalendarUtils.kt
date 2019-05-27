package com.example.voiceassistant.Util

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.text.format.DateUtils
import android.util.Log
import com.example.voiceassistant.Retrofit.RetrofitClient
import java.text.SimpleDateFormat
import java.util.*

class CalendarUtils(ctx: Context) {
    val context = ctx

    private val EVENT_PROJECTION: Array<String> = arrayOf(
        CalendarContract.Calendars._ID,                     // 0
        CalendarContract.Calendars.ACCOUNT_NAME,            // 1
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
        CalendarContract.Calendars.OWNER_ACCOUNT            // 3
    )

    private val PROJECTION_ID_INDEX: Int = 0
    private val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
    private val PROJECTION_DISPLAY_NAME_INDEX: Int = 2

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

    fun getTodayEvents(): MutableList<String>{

        val events = mutableListOf<String>()
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?))"
        val selectionArgs: Array<String> = arrayOf("danny27995@gmail.com")
        val cur: Cursor = context.contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)

        while (cur.moveToNext()) {
            val calID: Long = cur.getLong(PROJECTION_ID_INDEX)
            val displayName: String = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
            val accountName: String = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)

            Log.d(RetrofitClient.TAG, "CalId: $calID | name: $displayName | account: $accountName")

            if (calID.toInt() == 7) {
                val builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon()
                val now = Date().time

                ContentUris.appendId(builder, now - DateUtils.DAY_IN_MILLIS * 20)
                ContentUris.appendId(builder, now + DateUtils.DAY_IN_MILLIS * 20)

                val eventCursor = context.contentResolver.query(
                    builder.build(),
                    arrayOf("calendar_id", "title", "description", "dtstart", "dtend", "eventLocation"),
                    null, null, "dtstart ASC"
                )

                eventCursor.moveToFirst()

                for (i in 0 until eventCursor.count) {
                    val nameEvents = eventCursor.getString(1)
                    val startDates = getDate(eventCursor.getString(3).toLong())

                    if (startDates.equals(TimeUtils.getTodayDate())) {
                        events.add(nameEvents)
                    }

                    eventCursor.moveToNext()
                }
                return events
            }
        }

        return events
    }

    fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}