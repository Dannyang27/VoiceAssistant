package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Adapter.HistorialAdapter
import com.example.voiceassistant.R

class WeatherHistoryFragment : Fragment(){

    private lateinit var historialRV: RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var viewAdapter : RecyclerView.Adapter<*>

    companion object {
        fun newInstance(): WeatherHistoryFragment = WeatherHistoryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.history_fragment, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = HistorialAdapter(mutableListOf("1", "2", "3","1", "2", "3","1", "2", "3"))

        historialRV = view.findViewById<RecyclerView>(R.id.weather_history_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }
}