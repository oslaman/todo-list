package com.oslaman.todolist.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.oslaman.todolist.domain.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDosDao {
    @Query("select * from ToDoEntity")
    fun getTodos(): Flow<List<ToDoEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodos(users: List<ToDoEntity>)

    @Update()
    fun updateTodo(todo: ToDoEntity)

    @Query("DELETE FROM ToDoEntity WHERE id = :todoId")
    fun deleteById(todoId: String): Int

    @Query("UPDATE ToDoEntity SET isDone = :completed WHERE id = :todoId")
    fun updateCompleted(todoId: Int, completed: Boolean)

    @Query("DELETE FROM ToDoEntity")
    fun deleteAll()

    @Query("DELETE FROm ToDoEntity WHERE isDone = 1")
    fun deleteCompleted(): Int
}

@Database(entities = [ToDoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val todosDao: ToDosDao
}