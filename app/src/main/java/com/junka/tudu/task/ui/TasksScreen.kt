package com.junka.tudu.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.junka.tudu.task.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val showDialog by tasksViewModel.showDialog.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val state by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading,
        key1 = lifecycle,
        key2 = tasksViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.state.collect { value = it }
        }
    }


    when (state) {
        is TasksUiState.Error -> {

        }
        TasksUiState.Loading -> {

        }
        is TasksUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTaskDialog(show = showDialog, onDismiss = {
                    tasksViewModel.onDialogClose()
                }, onSave = { task ->
                    tasksViewModel.onTaskCreated(task)
                })
                FabDialog(
                    modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp), tasksViewModel
                )
                TasksList((state as TasksUiState.Success).tasks,
                    onItemChecked = { tasksViewModel.onTaskChecked(it) },
                    onItemTap = { tasksViewModel.onItemRemove(it) })
            }
        }
    }
}

@Composable
fun TasksList(
    myTasks: List<TaskModel>,
    onItemChecked: (TaskModel) -> Unit,
    onItemTap: (TaskModel) -> Unit
) {

    LazyColumn {
        items(myTasks, key = { it.id }) { task ->
            ItemTask(
                task = task,
                onItemChecked = { onItemChecked(task) },
                onItemTap = { onItemTap(task) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTask(task: TaskModel, onItemChecked: (TaskModel) -> Unit, onItemTap: (TaskModel) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onItemTap(task)
                })
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                task.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            Checkbox(checked = task.selected, onCheckedChange = { onItemChecked(task) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(onClick = {
        tasksViewModel.onDialogShow()
    }, modifier = modifier) {

        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onSave: (String) -> Unit) {

    var myTask by remember { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Text(text = "Add a new task", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTask, onValueChange = { myTask = it })
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onSave(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}


@Preview
@Composable
fun TaskScreenPreview() {

}