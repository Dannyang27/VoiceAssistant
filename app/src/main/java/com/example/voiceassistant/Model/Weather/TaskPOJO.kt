package com.example.voiceassistant.Model.Weather

import java.util.*

data class TaskPOJO (val id: String, var isDone: Int, val text: String){
    companion object{
        val TABLE_NAME = "Task"
        val COLUMN_ID = "id"
        val COLUMN_DONE = "isDone"
        val COLUMN_TEXT = "text"
    }
}