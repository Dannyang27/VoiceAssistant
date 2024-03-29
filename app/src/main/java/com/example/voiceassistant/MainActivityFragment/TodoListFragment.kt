package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.voiceassistant.Adapter.TodoListAdapter
import com.example.voiceassistant.Database.TaskRepository
import com.example.voiceassistant.Model.Weather.TaskPOJO
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.SwipeToDeleteCallback
import com.example.voiceassistant.Viewholder.HorizontalDivider
import com.example.voiceassistant.Viewholder.TodoListViewHolder

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

        todolist.addAll(TaskRepository(activity?.applicationContext!!).getAllTasks())
        todolist.sortBy { it.isDone }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TodoListAdapter(todolist)

        val swipeHandler = object: SwipeToDeleteCallback(activity?.applicationContext!!){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                val adapter = viewAdapter as TodoListAdapter
                adapter.removeAt(viewHolder.adapterPosition)

                val vh = viewHolder as TodoListViewHolder
                TaskRepository(activity?.applicationContext!!).delete(vh.id)
            }
        }

        todolistRecyclerView = view.findViewById<RecyclerView>(R.id.todolist_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(todolistRecyclerView)

        return view
    }
}