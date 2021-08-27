package dev.zurbaevi.todolist.repository

import androidx.lifecycle.LiveData
import dev.zurbaevi.todolist.database.TaskDao
import dev.zurbaevi.todolist.database.TaskEntry

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(taskEntry: TaskEntry) = taskDao.insert(taskEntry)

    suspend fun update(taskEntry: TaskEntry) = taskDao.update(taskEntry)

    suspend fun delete(taskEntry: TaskEntry) = taskDao.delete(taskEntry)

    fun getAllTasks(): LiveData<List<TaskEntry>> = taskDao.getAllTasks()

    fun getAllTasksCount(): LiveData<Int> = taskDao.getAllTasksCount()
}