package com.example.voiceassistant.Model.Weather

import java.util.*

data class TaskPOJO (var isDone: Boolean, val text: String, val date: String){
    companion object{
        val TABLE_NAME = "Task"
        val COLUMN_DONE = "isDone"
        val COLUMN_TEXT = "text"
        val COLUMN_DATE = "date"
    }
}