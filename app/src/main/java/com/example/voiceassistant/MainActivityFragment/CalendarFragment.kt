package com.example.voiceassistant.MainActivityFragment

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import com.example.voiceassistant.Activity.TodoListActivity
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Viewholder.HorizontalDivider
import com.example.voiceassistant.Viewholder.TodoListViewHolder
import java.util.*
import java.text.SimpleDateFormat


class CalendarFragment : Fragment(){

    lateinit var viewManager : RecyclerView.LayoutManager
    lateinit var todolistRecyclerView: RecyclerView

    var date = TimeUtils.getTodayDate()
    var todolist = mutableListOf<TaskPOJO>()

    private val EVENT_PROJECTION: Array<String> = arrayOf(
        CalendarContract.Calendars._ID,                     // 0
        CalendarContract.Calendars.ACCOUNT_NAME,            // 1
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
        CalendarContract.Calendars.OWNER_ACCOUNT            // 3
    )

    private val PROJECTION_ID_INDEX: Int = 0
    private val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
    private val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
    private val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

    lateinit var contentResolver : ContentResolver


    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
        lateinit var viewAdapter : RecyclerView.Adapter<TodoListViewHolder>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.voiceassistant.R.layout.calendar_fragment , container, false)
        val calendar = view.findViewById<CalendarView>(com.example.voiceassistant.R.id.calendar)
        val todolistTitle = view.findViewById<TextView>(com.example.voiceassistant.R.id.todolist_date)
        todolistTitle.text = date

        val viewAllBtn = view.findViewById<Button>(com.example.voiceassistant.R.id.view_all_button)

        calendar.setOnDateChangeListener{ _ , year, month, dayOfMonth ->
            date = getDataFormatted(dayOfMonth, month + 1, year)
            todolistTitle.text = date
            viewAdapter.notifyDataSetChanged()
        }

        viewAllBtn.setOnClickListener {
            val intent = Intent(activity, TodoListActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TodoListAdapter(todolist)

        todolistRecyclerView = view.findViewById<RecyclerView>(com.example.voiceassistant.R.id.todo_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        contentResolver = activity?.contentResolver!!
        Log.d(RetrofitClient.TAG, "Running TestReadCalendar")
        testReadCalendar()
        Log.d(RetrofitClient.TAG, "Ending TestReadCalendar")

        return view
    }

    private fun getDataFormatted(day: Int, month: Int, year: Int): String{
        var date = ""
        if(day < 10){
            date += "0$day"
        }else{
            date += day
        }
        if(month < 10){
            date += "/0$month"
        }else{
            date +="/$month"
        }

        return date.plus("/$year")
    }

    private fun testReadCalendar(){
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?))"
        val selectionArgs: Array<String> = arrayOf("danny27995@gmail.com")
        val cur: Cursor = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)

        while (cur.moveToNext()) {
            // Get the field values
            val calID: Long = cur.getLong(PROJECTION_ID_INDEX)
            val displayName: String = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
            val accountName: String = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)

            Log.d(RetrofitClient.TAG, "CalId: $calID | name: $displayName | account: $accountName")

            if(calID.toInt() == 1){
                val builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon()
                val now = Date().time

                ContentUris.appendId(builder, now - DateUtils.DAY_IN_MILLIS * 20)
                ContentUris.appendId(builder, now + DateUtils.DAY_IN_MILLIS * 20)

                val eventCursor = contentResolver.query(builder.build(),
                    arrayOf("calendar_id", "title", "description", "dtstart", "dtend", "eventLocation" ),
                    null, null, null)

                eventCursor.moveToFirst()

                for( i in 0 until eventCursor.count){
                    val nameEvents = eventCursor.getString(1)
                    val startDates = getDate(eventCursor.getString(3).toLong())
                    val endDates = getDate(eventCursor.getString(4).toLong())
                    val description = eventCursor.getShort(2)

                    Log.d(RetrofitClient.TAG, "$nameEvents | $startDates | $endDates | $description")
                    eventCursor.moveToNext()
                }
                return
            }
        }
    }

    fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}