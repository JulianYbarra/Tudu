package com.junka.tudu.task.ui

import com.junka.tudu.task.ui.model.TaskModel

sealed interface TasksUiState {
    object Loading : TasksUiState
    data class Error(val throwable: Throwable) : TasksUiState
    data class Success(val tasks : List<TaskModel>) : TasksUiState
}