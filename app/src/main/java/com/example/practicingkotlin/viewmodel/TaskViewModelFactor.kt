package com.example.practicingkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicingkotlin.repository.TaskRepository

class TaskViewModelFactor(private val repository: TaskRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")

    }

}