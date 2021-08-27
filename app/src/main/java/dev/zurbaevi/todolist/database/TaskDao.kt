package dev.zurbaevi.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntry: TaskEntry)

    @Update
    suspend fun update(taskEntry: TaskEntry)

    @Delete
    suspend fun delete(taskEntry: TaskEntry)

    @Query("SELECT * FROM task_table ORDER BY timestamp DESC")
    fun getAllTasks(): LiveData<List<TaskEntry>>

    @Query("SELECT COUNT(*) FROM task_table")
    fun getAllTasksCount(): LiveData<Int>
}