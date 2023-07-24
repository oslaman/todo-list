package com.oslaman.todolist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oslaman.todolist.ui.todos.ToDosScreen

@Composable
fun ToDoListApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.HOME) {
        composable(route = Route.HOME) { backStackEntry ->  
            ToDosScreen()
        }
    }
}

object Route {
    const val HOME = "home"
    const val CREATE = "create"
}