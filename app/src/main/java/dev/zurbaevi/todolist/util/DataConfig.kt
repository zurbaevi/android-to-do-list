package dev.zurbaevi.todolist.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    return SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
}