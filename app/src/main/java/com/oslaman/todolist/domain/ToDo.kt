package com.oslaman.todolist.domain

data class ToDo(
    val id: Int,
    val title: String,
    val date: String,
    val isDone: Boolean
)
