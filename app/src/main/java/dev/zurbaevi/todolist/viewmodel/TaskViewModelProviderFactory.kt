package dev.zurbaevi.todolist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.zurbaevi.todolist.service.repository.TaskRepository
import dev.zurbaevi.todolist.service.repository.local.TaskDatabase

@Suppress("UNCHECKED_CAST")
class TaskViewModelProviderFactory(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: TaskViewModelProviderFactory? = null

        fun getInstance(context: Context): TaskViewModelProviderFactory =
            instance ?: synchronized(this) {
                instance ?: TaskViewModelProviderFactory(
                    TaskRepository(
                        TaskDatabase.getDatabase(
                            context
                        ).taskDao()
                    )
                )
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository) as T
    }
}