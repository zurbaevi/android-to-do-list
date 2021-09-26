package dev.zurbaevi.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.zurbaevi.todolist.data.Constants.DATABASE_VERSION
import dev.zurbaevi.todolist.data.model.TaskEntry

@Database(entities = [TaskEntry::class], version = DATABASE_VERSION, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}