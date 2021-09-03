package dev.zurbaevi.todolist.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Calendar.getInstance().time)
