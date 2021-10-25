package dev.zurbaevi.todolist.di

import android.content.Context
import androidx.room.Room
import dev.zurbaevi.todolist.data.Constants
import dev.zurbaevi.todolist.data.local.TaskDatabase
import org.koin.dsl.module

val appModule = module {
    single { provideTaskDatabase(get()) }
    single { provideTaskDao(get()) }
}

private fun provideTaskDatabase(context: Context) = Room.databaseBuilder(
    context, TaskDatabase::class.java, Constants.DATABASE_NAME
).build()

private fun provideTaskDao(database: TaskDatabase) = database.taskDao()