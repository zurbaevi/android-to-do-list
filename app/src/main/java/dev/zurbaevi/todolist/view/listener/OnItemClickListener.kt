package dev.zurbaevi.todolist.view.listener

import dev.zurbaevi.todolist.model.TaskEntry

interface OnItemClickListener {

    fun onItemClick(taskEntry: TaskEntry)
    fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean)

}