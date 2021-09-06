package dev.zurbaevi.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.service.local.TaskDatabase
import dev.zurbaevi.todolist.service.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val taskRepository = TaskRepository(taskDao)

    val getAllTasks = taskRepository.getAllTasks()
    val getAllTasksCount = taskRepository.getAllTasksCount()

    fun insert(taskEntry: TaskEntry) {
        viewModelScope.launch {
            taskRepository.insert(taskEntry)
        }
    }

    fun update(taskEntry: TaskEntry, isChecked: Boolean = false) {
        viewModelScope.launch {
            taskRepository.update(taskEntry.copy(completed = isChecked))
        }
    }

    fun delete(taskEntry: TaskEntry) {
        viewModelScope.launch {
            taskRepository.delete(taskEntry)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    fun deleteCompletedTasks() {
        viewModelScope.launch {
            taskRepository.deleteCompletedTasks()
        }
    }
}