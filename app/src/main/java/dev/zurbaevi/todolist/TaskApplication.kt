package dev.zurbaevi.todolist

import android.app.Application
import dev.zurbaevi.todolist.di.appModule
import dev.zurbaevi.todolist.di.repositoryModule
import dev.zurbaevi.todolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskApplication)
            modules(appModule, viewModelModule, repositoryModule)
        }
    }

}