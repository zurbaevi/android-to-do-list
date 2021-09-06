package dev.zurbaevi.todolist.service.local

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.zurbaevi.todolist.model.TaskEntry

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntry: TaskEntry)

    @Update
    suspend fun update(taskEntry: TaskEntry)

    @Delete
    suspend fun delete(taskEntry: TaskEntry)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM task_table WHERE completed LIKE 1")
    suspend fun deleteCompletedTasks()

    @Query("SELECT * FROM task_table ORDER BY timestamp DESC")
    fun getAllTasks(): LiveData<List<TaskEntry>>

    @Query("SELECT COUNT(*) FROM task_table WHERE completed LIKE 0")
    fun getAllTasksCount(): LiveData<Int>
}