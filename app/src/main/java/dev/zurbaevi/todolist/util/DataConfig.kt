package dev.zurbaevi.todolist.util

import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(context: Context): String {
    val calendar = Calendar.getInstance()
    return SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
}