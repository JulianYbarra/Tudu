package com.junka.tudu.task.ui.model

data class TaskModel(
    val id : Int = System.currentTimeMillis().hashCode(),
    val task : String,
    val selected : Boolean = false
)