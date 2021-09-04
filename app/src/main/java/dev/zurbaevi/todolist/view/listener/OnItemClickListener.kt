package dev.zurbaevi.todolist.view.listener

import dev.zurbaevi.todolist.model.TaskEntry

interface OnItemClickListener {
    fun onItemClick(taskEntry: TaskEntry)
    fun onCheckClick(id: Int, title: String, timestamp: Long, completed: Boolean)
}