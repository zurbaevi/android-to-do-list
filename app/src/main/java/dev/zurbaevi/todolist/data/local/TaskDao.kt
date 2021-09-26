package dev.zurbaevi.todolist.data.local

import androidx.room.*
import dev.zurbaevi.todolist.data.model.TaskEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    fun getAllTasks(): Flow<List<TaskEntry>>

    @Query("SELECT COUNT(*) FROM task_table WHERE completed LIKE 0")
    fun getAllTasksCount(): Flow<Int>

}