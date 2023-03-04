package com.junka.tudu.task.domain

import com.junka.tudu.task.data.TaskRepository
import com.junka.tudu.task.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskModel: TaskModel) = taskRepository.deleteTask(taskModel)
}