package com.oslaman.todolist.repository

import com.oslaman.todolist.database.AppDatabase
import com.oslaman.todolist.database.ToDoEntity
import com.oslaman.todolist.database.asDomainModel
import com.oslaman.todolist.domain.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ToDosRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    val todos: Flow<List<ToDo>?> =
        appDatabase.todosDao.getTodos().map { it?.asDomainModel() }

    suspend fun insertTodos(toDo: ToDo) {
        try {
            appDatabase.todosDao.insertTodos(listOf(ToDoEntity(toDo.id, toDo.title, toDo.date.toString(), toDo.isDone)))
        } catch (e: Exception) {
            Timber.w(e)
        }

    }

    suspend fun deleteCompleted() {
        try {
            appDatabase.todosDao.deleteCompleted()
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    suspend fun deleteAll() {
        try {
            appDatabase.todosDao.deleteAll()
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    suspend fun deleteById(id: String) {
        try {
            appDatabase.todosDao.deleteById(id)
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    suspend fun updateToDo(id: Int, completed: Boolean) {
        try {
            appDatabase.todosDao.updateCompleted(id, completed)
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    suspend fun refreshTodos() {
        try {
            val users = appDatabase.todosDao.getTodos()
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}