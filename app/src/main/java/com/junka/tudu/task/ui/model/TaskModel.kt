package com.junka.tudu.task.ui.model

data class TaskModel(
    val id : Long = System.currentTimeMillis(),
    val task : String,
    val selected : Boolean = false
)