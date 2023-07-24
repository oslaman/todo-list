package com.oslaman.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oslaman.todolist.domain.ToDo

@Entity
data class ToDoEntity constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val date: String,
    val isDone: Boolean
)

fun List<ToDoEntity>.asDomainModel(): List<ToDo> {
    return map {
        ToDo(
            id = it.id,
            title = it.title,
            date = it.date,
            isDone = it.isDone
        )
    }
}