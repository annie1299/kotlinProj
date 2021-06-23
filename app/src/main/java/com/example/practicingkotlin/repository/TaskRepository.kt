package com.example.practicingkotlin.repository;

import com.example.practicingkotlin.database.TaskDao
import com.example.practicingkotlin.database.TaskEntry

class TaskRepository(private val taskDao: TaskDao) {

    val tasks = taskDao.getAllTasks()

    suspend fun insert(taskEntry: TaskEntry) = taskDao.insert(taskEntry)

    suspend fun update(taskEntry: TaskEntry) = taskDao.update(taskEntry)

    suspend fun delete(taskEntry: TaskEntry) = taskDao.delete(taskEntry)

    suspend fun deleteAll()  {taskDao.deleteAll()}
}