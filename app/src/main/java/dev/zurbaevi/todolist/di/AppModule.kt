package dev.zurbaevi.todolist.di

import androidx.room.Room
import dev.zurbaevi.todolist.data.Constants
import dev.zurbaevi.todolist.data.local.TaskDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), TaskDatabase::class.java, Constants.DATABASE_NAME
        ).build().taskDao()
    }
}
