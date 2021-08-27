package dev.zurbaevi.todolist.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DateFormat

@BindingAdapter("setTimestamp")
fun setTimestamp(view: TextView, timestamp: Long) {
    view.text = DateFormat.getInstance().format(timestamp)
}