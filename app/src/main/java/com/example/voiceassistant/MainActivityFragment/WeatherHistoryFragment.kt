package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Adapter.HistorialAdapter
import com.example.voiceassistant.Database.WeatherRepository
import com.example.voiceassistant.Model.Weather.WeatherPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient

class WeatherHistoryFragment : Fragment(){

    private lateinit var historialRV: RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        var historial = mutableListOf<WeatherPOJO>()
        lateinit var viewAdapter: RecyclerView.Adapter<*>
        fun newInstance(): WeatherHistoryFragment = WeatherHistoryFragment()

        fun addWeather(weather: WeatherPOJO){
            historial.add(weather)
            viewAdapter.notifyDataSetChanged()
        }

        fun addAllWeather( weathers : MutableList<WeatherPOJO>){
            historial.addAll(weathers)
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.history_fragment, container, false)

        historial = WeatherRepository(activity?.applicationContext!!).findAll()
        viewManager = LinearLayoutManager(activity)
        viewAdapter = HistorialAdapter(historial)

        historialRV = view.findViewById<RecyclerView>(R.id.weather_history_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }
}