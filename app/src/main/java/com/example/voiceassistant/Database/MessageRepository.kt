package com.example.voiceassistant.Database

import android.content.Context
import android.util.Log
import com.example.voiceassistant.Model.Weather.MessagePOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update
import org.jetbrains.anko.db.delete

class MessageRepository (val context: Context){

    fun findAll(): MutableList<MessagePOJO> = context.database.use{
        val messages = mutableListOf<MessagePOJO>()

        select(MessagePOJO.TABLE_NAME, MessagePOJO.COLUMN_QUERY, MessagePOJO.COLUMN_RESPONSE, MessagePOJO.COLUMN_DATE)
            .parseList(object: MapRowParser<MutableList<MessagePOJO>>{
                override fun parseRow(columns: Map<String, Any?>): MutableList<MessagePOJO> {
                    val query = columns.getValue(MessagePOJO.COLUMN_QUERY)
                    val response = columns.getValue(MessagePOJO.COLUMN_RESPONSE)
                    val date = columns.getValue(MessagePOJO.COLUMN_DATE)

                    val message = MessagePOJO(query.toString(), response.toString(), date.toString())
                    messages.add(message)
                    return messages
                }
            })
        messages
    }

    fun insert(message: MessagePOJO) = context.database.use {
        insert(MessagePOJO.TABLE_NAME,
            MessagePOJO.COLUMN_QUERY to message.query,
            MessagePOJO.COLUMN_RESPONSE to message.response,
            MessagePOJO.COLUMN_DATE to message.date)
    }

    fun update(message: MessagePOJO) = context.database.use {
        val updateResult = update(MessagePOJO.TABLE_NAME,
            MessagePOJO.COLUMN_QUERY to message.query,
            MessagePOJO.COLUMN_RESPONSE to message.response,
            MessagePOJO.COLUMN_DATE to message.date)
            .whereArgs("${MessagePOJO.COLUMN_DATE} = ${message.date}", MessagePOJO.COLUMN_DATE to message.date)

        Log.d(RetrofitClient.TAG, "Update result code is $updateResult")
    }

    fun deleteByDate(date : String) = context.database.use {
        delete(MessagePOJO.TABLE_NAME,  "date={date}", "date" to date)
    }
}