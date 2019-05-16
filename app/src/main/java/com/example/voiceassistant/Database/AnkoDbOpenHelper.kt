package com.example.voiceassistant.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.voiceassistant.Model.Weather.MessagePOJO
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import org.jetbrains.anko.db.*

class AnkoDbOpenHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "WeatherDatabase", null, 1) {

    init {
        instance = this
    }

    companion object {
        private var instance: AnkoDbOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: AnkoDbOpenHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(WeatherPOJO.TABLE_NAME, true,
            WeatherPOJO.COLUMN_CITY to TEXT,
            WeatherPOJO.COLUMN_TEMP to REAL,
            WeatherPOJO.COLUMN_HUMIDITY to REAL,
            WeatherPOJO.COLUMN_CLIMA to TEXT,
            WeatherPOJO.COLUMN_DATE to TEXT,
            WeatherPOJO.COLUM_QUERY to TEXT,
            WeatherPOJO.COLUMN_DAYOFWEEK to TEXT)

        Log.d(RetrofitClient.TAG, "Creating table WeatherPOJO${WeatherPOJO.TABLE_NAME}")

        db.createTable(MessagePOJO.TABLE_NAME, true,
            MessagePOJO.COLUMN_QUERY to TEXT,
            MessagePOJO.COLUMN_RESPONSE to TEXT,
            MessagePOJO.COLUMN_DATE to TEXT)

        Log.d(RetrofitClient.TAG, "Creating table MessagePOJO ${MessagePOJO.TABLE_NAME}")

        db.createTable(TaskPOJO.TABLE_NAME, true,
            TaskPOJO.COLUMN_ID to INTEGER,
            TaskPOJO.COLUMN_DONE to TEXT,
            TaskPOJO.COLUMN_TEXT to TEXT)

        Log.d(RetrofitClient.TAG, "Creating table TaskPOJO ${TaskPOJO.TABLE_NAME}")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(WeatherPOJO.TABLE_NAME, true)
        db.dropTable(MessagePOJO.TABLE_NAME, true)
        db.dropTable(TaskPOJO.TABLE_NAME, true)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}

val Context.database: AnkoDbOpenHelper
    get() = AnkoDbOpenHelper.getInstance(this)