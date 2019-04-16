package com.example.voiceassistant.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
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
            WeatherPOJO.COLUMN_IMAGE to TEXT,
            WeatherPOJO.COLUM_QUERY to TEXT)
        Log.d(RetrofitClient.TAG, "Creating table ${WeatherPOJO.TABLE_NAME}")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(WeatherPOJO.TABLE_NAME, true)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}

val Context.database: AnkoDbOpenHelper
    get() = AnkoDbOpenHelper.getInstance(this)