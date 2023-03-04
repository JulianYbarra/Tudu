package com.junka.tudu.task.data

import com.junka.tudu.task.data.model.TaskEntity
import com.junka.tudu.task.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    val tasks : Flow<List<TaskModel>> = taskDao.getTasks().map { it.map { it.toModel() } }

    suspend fun addTask(taskModel: TaskModel) = taskDao.insert(taskModel.toEntity())

    suspend fun updateTask(taskModel: TaskModel) = taskDao.updateTask(taskModel.toEntity())

    suspend fun deleteTask(taskModel: TaskModel) = taskDao.deleteTask(taskModel.toEntity())
}

fun TaskModel.toEntity() = TaskEntity(id,task,selected)

fun TaskEntity.toModel() = TaskModel(id,task,selected)