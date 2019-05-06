package com.example.voiceassistant.Database

import android.content.Context
import com.example.voiceassistant.Model.Task
import com.example.voiceassistant.Model.Weather.TaskPOJO
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import java.nio.file.Files.delete

class TaskRepository (val context: Context){

    fun findTaskByDate(): MutableList<TaskPOJO> = context.database.use {
        val tasks = mutableListOf<TaskPOJO>()

        select(TaskPOJO.TABLE_NAME, TaskPOJO.COLUMN_DONE, TaskPOJO.COLUMN_TEXT, TaskPOJO.COLUMN_DATE)
            .parseList(object: MapRowParser<MutableList<TaskPOJO>>{
                override fun parseRow(columns: Map<String, Any?>): MutableList<TaskPOJO> {
                    val isDone = columns.getValue(TaskPOJO.COLUMN_DONE)
                    val text = columns.getValue(TaskPOJO.COLUMN_TEXT)
                    val date = columns.getValue(TaskPOJO.COLUMN_DATE)

                    val task = TaskPOJO(isDone.toString() == "true", text.toString(), date.toString())
                    tasks.add(task)
                    return tasks
                }
            })
        tasks
    }


    fun insert(task: TaskPOJO) = context.database.use {
        insert(TaskPOJO.TABLE_NAME,
            TaskPOJO.COLUMN_DONE to task.isDone,
            TaskPOJO.COLUMN_TEXT to task.text,
            TaskPOJO.COLUMN_DATE to task.date)
    }
}