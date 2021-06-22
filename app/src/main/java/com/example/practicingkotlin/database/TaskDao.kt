package com.example.practicingkotlin.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import kotlinx.coroutines.flow.Flow

import java.util.List;

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntry: TaskEntry)

    @Delete
    suspend fun delete(taskEntry: TaskEntry)

    @Update
    suspend fun update(taskEntry: TaskEntry)


    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntry>>

}
