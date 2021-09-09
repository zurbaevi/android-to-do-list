package dev.zurbaevi.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _liveData: MutableLiveData<Boolean> = MutableLiveData()
    val liveData: LiveData<Boolean>
        get() = _liveData

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(1000)
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        _liveData.value = true
    }
}