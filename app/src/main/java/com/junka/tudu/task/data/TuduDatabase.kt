package com.junka.tudu.task.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.junka.tudu.task.data.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TuduDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao
}