package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Viewholder.HorizontalDivider

class TodoListFragment : Fragment() {
    lateinit var todolistRecyclerView: RecyclerView
    lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        fun newInstance(): TodoListFragment = TodoListFragment()
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        var todolist = mutableListOf<TaskPOJO>()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.todo_list_fragment , container, false)

        //TaskRepository(activity?.applicationContext!!).delete(4)

        todolist.addAll(TaskRepository(activity?.applicationContext!!).getAllTasks())
        viewManager = LinearLayoutManager(activity)
        viewAdapter = TodoListAdapter(todolist)

        todolistRecyclerView = view.findViewById<RecyclerView>(R.id.todolist_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }

    private fun loadData(list: MutableList<TaskPOJO>){
        list.add(TaskPOJO(1,false, "Task1"))
        list.add(TaskPOJO(2,false, "Task2"))
        list.add(TaskPOJO(3,false, "Task3"))
        list.add(TaskPOJO(4,false, "Task4"))
        list.add(TaskPOJO(5,false, "Task5"))
        list.add(TaskPOJO(6,false, "Task6"))
        list.add(TaskPOJO(7,false, "Task7"))
        list.add(TaskPOJO(8,false, "Task8"))
        list.add(TaskPOJO(9,false, "Task9"))
        list.add(TaskPOJO(10,false, "Task10"))
    }

    private fun insertData(){
        val ctx = activity?.applicationContext!!
        val lastId = TaskRepository(ctx).getLastId()
        val task1 = TaskPOJO(lastId + 1, false, "Watch Game Of Thrones")
        val task2 = TaskPOJO(lastId + 2, false, "Google I/O Keynote")
        val task3 = TaskPOJO(lastId + 3, false, "Oneplus 7 Pro Presentation")
        TaskRepository(ctx).insert(task1)
        TaskRepository(ctx).insert(task2)
        TaskRepository(ctx).insert(task3)


        Log.d(RetrofitClient.TAG, "Task Added correctly")
    }
}