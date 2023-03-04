package com.junka.tudu.task.data

import androidx.room.*
import com.junka.tudu.task.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * from TaskEntity")
    fun getTasks() : Flow<List<TaskEntity>>

    @Insert
    suspend fun insert(taskEntity: TaskEntity) : Long

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity) : Int
}