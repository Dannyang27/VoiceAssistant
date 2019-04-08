package com.example.voiceassistant.Util

import android.util.Log

object VoiceFilterUtils{
//    val cityHashmap = mapOf(
//        Pair(6356055, "Barcelona"),
//        Pair(6058560, "Londres"),
//        Pair(2518878, "Denia"),
//        Pair(2509954, "Valencia"),
//        Pair(6359304, "Madrid"),
//        Pair(2759794, "Amsterdam"),
//        Pair(1850147, "Tokio"),
//        Pair(6455259, "Paris"),
//        Pair(2950158, "Berlin")
//        )

    val cityHashmap = mapOf(
        Pair("Barcelona", 6356055),
        Pair("Londres", 6058560),
        Pair("Denia", 2518878),
        Pair("Valencia", 2509954),
        Pair("Madrid", 6359304),
        Pair("Amsterdam", 2759794),
        Pair("Tokio", 1850147),
        Pair("Paris", 6455259),
        Pair("Berlin", 2950158)
    )

    fun getIdByCity(words: List<String>) : Int{
    Log.d("Weather", words.toString())
        for (word in words){
            for(key in cityHashmap.keys){
                if(key == word){
                    return cityHashmap[key]!!
                }
            }
        }

        return -1
    }
}