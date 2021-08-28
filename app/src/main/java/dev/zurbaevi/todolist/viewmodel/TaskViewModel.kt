package dev.zurbaevi.todolist.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.zurbaevi.todolist.database.TaskDatabase
import dev.zurbaevi.todolist.database.TaskEntry
import dev.zurbaevi.todolist.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val taskRepository = TaskRepository(taskDao)

    val getAllTasks = taskRepository.getAllTasks()
    val getAllTasksCount = taskRepository.getAllTasksCount()

    fun insert(taskEntry: TaskEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insert(taskEntry)
        }
    }

    fun update(taskEntry: TaskEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.update(taskEntry)
        }
    }

    fun delete(taskEntry: TaskEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.delete(taskEntry)
        }
    }
}