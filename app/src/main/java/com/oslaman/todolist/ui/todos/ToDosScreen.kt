package com.oslaman.todolist.ui.todos

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.oslaman.todolist.domain.ToDo
import java.time.Instant
import java.util.Date
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ToDosScreen() {
    val viewModel = hiltViewModel<ToDosViewModel>()
    val uiState = viewModel.uiState

    val openDialog = rememberSaveable { mutableStateOf(false) }
    val todoTitle = rememberSaveable { mutableStateOf("") }



    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Add ToDo")
            },
            text = {
                TextField(
                    value = todoTitle.value,
                    onValueChange = { todoTitle.value = it },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addToDo(
                            ToDo(
                                Random.nextInt(),
                                todoTitle.value,
                                Date.from(Instant.now()).toString(),
                                false
                            )
                        )
                        openDialog.value = false
                        todoTitle.value = ""
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold (
        topBar = { LargeTopAppBar(title = { Text("ToDo list") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openDialog.value = true
            }) {
                Icon(imageVector = Icons.Filled.Add, null)
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn() {
                items(uiState.list) { item ->
                    ListItem(
                        headlineText = { Text(item.title, style =
                        TextStyle(
                            textDecoration = if(item.isDone) {
                            TextDecoration.LineThrough
                        } else {TextDecoration.None})) },
                        leadingContent = { Checkbox(checked = item.isDone, onCheckedChange = { viewModel.updateToDo(item.id, !item.isDone) } ) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ToDoScreenPreview() {
    ToDosScreen()
}