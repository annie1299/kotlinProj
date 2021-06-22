package com.example.practicingkotlin.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntry (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    var id: Int,
    @ColumnInfo(name = "task_title")
    var title: String,
    @ColumnInfo(name = "task_desc")
    var description:String
)