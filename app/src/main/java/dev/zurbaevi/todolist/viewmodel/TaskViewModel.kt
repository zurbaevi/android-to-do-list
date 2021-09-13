package dev.zurbaevi.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.service.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

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