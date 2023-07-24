package com.oslaman.todolist.ui.todos

import com.oslaman.todolist.domain.ToDo

data class ToDosUiState(
    val list: List<ToDo> = listOf()
)
