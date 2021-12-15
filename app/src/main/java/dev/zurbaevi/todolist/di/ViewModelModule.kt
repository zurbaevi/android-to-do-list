package dev.zurbaevi.todolist.di

import dev.zurbaevi.todolist.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TaskViewModel(get()) }
}