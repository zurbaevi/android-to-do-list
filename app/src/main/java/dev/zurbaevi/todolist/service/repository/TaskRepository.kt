package dev.zurbaevi.todolist.service.repository

import androidx.lifecycle.LiveData
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.service.repository.local.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(taskEntry: TaskEntry) {
        withContext(Dispatchers.IO) {
            taskDao.insert(taskEntry)
        }
    }

    suspend fun update(taskEntry: TaskEntry) {
        withContext(Dispatchers.IO) {
            taskDao.update(taskEntry)
        }
    }

    suspend fun delete(taskEntry: TaskEntry) {
        withContext(Dispatchers.IO) {
            taskDao.delete(taskEntry)
        }
    }

    suspend fun deleteAllTasks() {
        withContext(Dispatchers.IO) {
            taskDao.deleteAllTasks()
        }
    }

    suspend fun deleteCompletedTasks() {
        withContext(Dispatchers.IO) {
            taskDao.deleteCompletedTasks()
        }
    }

    fun getAllTasks(): LiveData<List<TaskEntry>> = taskDao.getAllTasks()

    fun getAllTasksCount(): LiveData<Int> = taskDao.getAllTasksCount()

}