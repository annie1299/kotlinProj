package com.example.practicingkotlin.ui;

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicingkotlin.R
import com.example.practicingkotlin.RecyclerViewAdapter
import com.example.practicingkotlin.database.TaskDatabase
import com.example.practicingkotlin.database.TaskEntry
import com.example.practicingkotlin.database.TaskDao
import com.example.practicingkotlin.databinding.ActivityMainBinding
import com.example.practicingkotlin.repository.TaskRepository
import com.example.practicingkotlin.viewmodel.TaskViewModel
import com.example.practicingkotlin.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        val dao = TaskDatabase.getInstance(
            application
        ).TaskDao
        val repository =
            TaskRepository(dao)
        val factory =
            TaskViewModelFactory(repository)
        taskViewModel  = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        binding.myViewModel = taskViewModel
        binding.lifecycleOwner = this

        taskViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            RecyclerViewAdapter({ selectedItem: TaskEntry ->
                listItemClicked(selectedItem)
            })
        binding.taskRecyclerView.adapter = adapter
        displayTasksList()
    }

    private fun displayTasksList() {
        taskViewModel.getSavedTasks().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(taskEntry: TaskEntry) {
        taskViewModel.initUpdateAndDelete(taskEntry)
    }
}