package dev.zurbaevi.todolist.di

import dev.zurbaevi.todolist.data.repository.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TaskRepository(get()) }
}