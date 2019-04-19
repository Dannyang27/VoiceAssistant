package com.example.voiceassistant.Model.Weather

data class MessagePOJO (val query: String, val response: String, val date: String){
    companion object{
        val TABLE_NAME = "MessageHistorial"
        val COLUMN_QUERY = "query"
        val COLUMN_RESPONSE = "response"
        val COLUMN_DATE = "date"
    }
}