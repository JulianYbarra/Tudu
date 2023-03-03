package com.junka.tudu.task.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junka.tudu.task.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.selects.select
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(

) : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onDialogShow() {
        _showDialog.value = true
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        _tasks.add(TaskModel(task = task))
    }

    fun onTaskChecked(task: TaskModel) {
        val index = _tasks.indexOf(task)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }
}