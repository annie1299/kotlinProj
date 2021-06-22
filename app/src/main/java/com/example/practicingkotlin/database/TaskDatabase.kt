package com.example.practicingkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TaskEntry::class), version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract val TaskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).build()
                }
                return instance
            }
        }
    }
}