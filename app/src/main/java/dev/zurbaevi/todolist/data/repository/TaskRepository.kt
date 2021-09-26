package dev.zurbaevi.todolist.data.repository

import dev.zurbaevi.todolist.data.local.TaskDao
import dev.zurbaevi.todolist.data.model.TaskEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

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

    fun getAllTasks() = taskDao.getAllTasks()

    fun getAllTasksCount() = taskDao.getAllTasksCount()

}