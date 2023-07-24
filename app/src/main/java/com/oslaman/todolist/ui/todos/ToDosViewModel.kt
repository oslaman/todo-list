package com.oslaman.todolist.ui.todos

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.oslaman.todolist.database.AppDatabase
import com.oslaman.todolist.database.ToDoEntity
import com.oslaman.todolist.domain.ToDo
import com.oslaman.todolist.repository.ToDosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ToDosViewModel @Inject constructor(
    private val toDosRepository: ToDosRepository,
    application: Application
): AndroidViewModel(application = application) {
    var uiState by mutableStateOf(ToDosUiState())
        private set

    fun addToDo(todo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDosRepository.insertTodos(todo)
        }
    }

    fun updateToDo(id: Int, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toDosRepository.updateToDo(id, completed)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            toDosRepository.deleteCompleted()
            toDosRepository.todos.collect { list ->
                withContext(Dispatchers.Main) {
                    uiState = if (list.isNullOrEmpty()) {
                        uiState.copy()
                    } else {
                        uiState.copy(
                            list = list
                        )
                    }
                }
            }
        }
    }
}