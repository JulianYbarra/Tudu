package com.junka.tudu.task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junka.tudu.task.domain.AddTaskUseCase
import com.junka.tudu.task.domain.DeleteTaskUseCase
import com.junka.tudu.task.domain.GetTasksUseCase
import com.junka.tudu.task.domain.UpdateTaskUseCase
import com.junka.tudu.task.ui.TasksUiState.*
import com.junka.tudu.task.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    val state: StateFlow<TasksUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onDialogShow() {
        _showDialog.value = true
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task= task))
        }
    }

    fun onTaskChecked(task: TaskModel) {
       viewModelScope.launch {
           updateTaskUseCase(task.copy(selected = !task.selected))
       }
    }

    fun onItemRemove(task: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}