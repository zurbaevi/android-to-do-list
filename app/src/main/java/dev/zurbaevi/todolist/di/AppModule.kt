package dev.zurbaevi.todolist.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.zurbaevi.todolist.data.Constants
import dev.zurbaevi.todolist.data.local.TaskDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, TaskDatabase::class.java, Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideTaskDao(database: TaskDatabase) = database.taskDao()

}