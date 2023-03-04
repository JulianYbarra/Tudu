package com.junka.tudu.task.domain

import com.junka.tudu.task.data.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() = taskRepository.tasks
}