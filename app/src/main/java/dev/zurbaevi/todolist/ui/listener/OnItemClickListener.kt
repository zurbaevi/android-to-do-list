package dev.zurbaevi.todolist.ui.listener

import dev.zurbaevi.todolist.data.model.TaskEntry

interface OnItemClickListener {

    fun onItemClick(taskEntry: TaskEntry)
    fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean)

}