package com.example.voiceassistant.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Util.WeatherUtils
import com.squareup.picasso.Picasso

class WeatherInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_info)

        val toolbar = findViewById<Toolbar>(R.id.weather_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        toolbar.title = "Weather Info"

        val date = findViewById<TextView>(R.id.date_info)
        val query = findViewById<TextView>(R.id.query_info)
        val city = findViewById<TextView>(R.id.city_info)
        val clima = findViewById<TextView>(R.id.clima_info)
        val temp = findViewById<TextView>(R.id.temp_info)
        val humidity = findViewById<TextView>(R.id.humidity_info)
        val imageInfo = findViewById<ImageView>(R.id.image_info)


        date.text = intent.getStringExtra(WeatherPOJO.COLUMN_DATE)
        query.text = intent.getStringExtra(WeatherPOJO.COLUM_QUERY)
        city.text = intent.getStringExtra(WeatherPOJO.COLUMN_CITY)
        val climaInfo = intent.getStringExtra(WeatherPOJO.COLUMN_CLIMA)
        clima.text = climaInfo
        temp.text = "${intent.getDoubleExtra(WeatherPOJO.COLUMN_TEMP, 0.0)}º"
        humidity.text = "${intent.getDoubleExtra(WeatherPOJO.COLUMN_HUMIDITY, 0.0)}%"

        val image = WeatherUtils.getImageByWeather(climaInfo)
        Picasso.with(this)
            .load(image)
            .placeholder(image)
            .into(imageInfo)
    }
}
