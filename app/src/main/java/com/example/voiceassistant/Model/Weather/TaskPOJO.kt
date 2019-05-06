package com.example.voiceassistant.Model.Weather

data class TaskPOJO (val isDone: Boolean, val text: String){
    companion object{
        val TABLE_NAME = "Task"
        val COLUMN_DONE = "isDone"
        val COLUMN_TEXT = "text"
        val COLUMN_DATE = "date"
    }
}