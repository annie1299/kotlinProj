package com.example.practicingkotlin.viewmodel

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.*
import com.example.practicingkotlin.database.TaskDao
import com.example.practicingkotlin.database.TaskEntry
import com.example.practicingkotlin.repository.TaskRepository
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class TaskViewModel(private val repository: TaskRepository): ViewModel()  {

    val tasks = repository.tasks

    private var isUpdateOrDelete = false
    private lateinit var taskToUpdateOrDelete: TaskEntry

    val inputTask = MutableLiveData<String>()
    val inputDesc = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(taskEntry: TaskEntry) {
        inputTask.value = taskEntry.title
        inputDesc.value = taskEntry.description
        isUpdateOrDelete = true
        taskToUpdateOrDelete = taskEntry
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }


    fun saveOrUpdate() {
        if (inputTask.value == null) {
            statusMessage.value = Event("add task")
        } else {
            if (isUpdateOrDelete) {
                taskToUpdateOrDelete.title = inputTask.value!!
                taskToUpdateOrDelete.description = inputDesc.value!!
                //update(taskToUpdateOrDelete)
            } else {
                val task = inputTask.value!!
                val desc = inputDesc.value!!
                insert(TaskEntry(0, task, desc))
                inputTask.value = ""
                inputDesc.value = ""
            }
        }
    }

    private fun insert(taskEntry: TaskEntry) = viewModelScope.launch {
        val newRowId = repository.insert(taskEntry)
    }

    fun getSavedTasks() = liveData {
        repository.tasks.collect {
            emit(it)
        }
    }

    private fun delete(taskEntry: TaskEntry) = viewModelScope.launch {
        repository.delete(taskEntry)
    }



}