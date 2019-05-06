package com.example.voiceassistant.Database

import android.content.Context
import android.util.Log
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.Retrofit.RetrofitClient
import org.jetbrains.anko.db.*

class TaskRepository (val context: Context){

    fun getLastId(): Int = context.database.use {
        val tasks = mutableListOf<TaskPOJO>()
        var cont = 0
        select(TaskPOJO.TABLE_NAME, TaskPOJO.COLUMN_ID, TaskPOJO.COLUMN_DONE, TaskPOJO.COLUMN_TEXT, TaskPOJO.COLUMN_DATE)
            .parseList(object: MapRowParser<MutableList<TaskPOJO>>{
                override fun parseRow(columns: Map<String, Any?>): MutableList<TaskPOJO> {
                    cont++
                    return tasks
                }
            })
        cont
    }

    fun findTaskByDate(date : String): MutableList<TaskPOJO> = context.database.use {
        val tasks = mutableListOf<TaskPOJO>()

        select(TaskPOJO.TABLE_NAME, TaskPOJO.COLUMN_ID, TaskPOJO.COLUMN_DONE, TaskPOJO.COLUMN_TEXT, TaskPOJO.COLUMN_DATE)
            .whereArgs("date={date}", "date" to date )
            .parseList(object: MapRowParser<MutableList<TaskPOJO>>{
                override fun parseRow(columns: Map<String, Any?>): MutableList<TaskPOJO> {
                    val id = columns.getValue(TaskPOJO.COLUMN_ID)
                    val isDone = columns.getValue(TaskPOJO.COLUMN_DONE)
                    val text = columns.getValue(TaskPOJO.COLUMN_TEXT)
                    val date = columns.getValue(TaskPOJO.COLUMN_DATE)

                    val task = TaskPOJO(id.toString().toInt(),isDone.toString() == "true", text.toString(), date.toString())
                    tasks.add(task)
                    Log.d(RetrofitClient.TAG, "task text: ${task.text} isDone: ${task.isDone}")

                    return tasks
                }
            })
        tasks
    }


    fun insert(task: TaskPOJO) = context.database.use {
        val lastItem = findTaskByDate(task.date).size + 1
        insert(TaskPOJO.TABLE_NAME,
            TaskPOJO.COLUMN_ID to lastItem,
            TaskPOJO.COLUMN_DONE to task.isDone,
            TaskPOJO.COLUMN_TEXT to task.text,
            TaskPOJO.COLUMN_DATE to task.date)
    }

    fun update(task: TaskPOJO) = context.database.use {
        val done = task.isDone
        update(TaskPOJO.TABLE_NAME,
            TaskPOJO.COLUMN_ID to task.id,
            TaskPOJO.COLUMN_DONE to !done,
            TaskPOJO.COLUMN_TEXT to task.text,
            TaskPOJO.COLUMN_DATE to task.date)
            .whereArgs("id={id}", "id" to task.id)
            .exec()

        Log.d(RetrofitClient.TAG, "Task id: ${task.id} Update isDone:  $done")
    }
}