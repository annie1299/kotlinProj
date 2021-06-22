package com.example.practicingkotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicingkotlin.MyRecyclerViewAdapter
import com.example.practicingkotlin.R
import com.example.practicingkotlin.RecyclerViewAdapter
import com.example.practicingkotlin.database.TaskDatabase
import com.example.practicingkotlin.databinding.ActivityMainBinding
import com.example.practicingkotlin.repository.TaskRepository
import com.example.practicingkotlin.viewmodel.TaskViewModelFactor
import com.example.practicingkotlin.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = TaskDatabase.getInstance(application).TaskDao
        val repository = TaskRepository(dao)
        val factory = TaskViewModelFactor(repository)
        taskViewModel = ViewModelProvider(this,factory).get(TaskViewModel::class.java)
        binding.myViewModel = taskViewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView(){
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        displayTasks()
    }

    private fun displayTasks(){
        TaskViewModel.getSavedTasks.observe(this, Observer {
            Log.i("MYTAG", it.toString)
            binding.tasksRecyclerView.adapter = RecyclerViewAdapter(it)
        })
    }

}