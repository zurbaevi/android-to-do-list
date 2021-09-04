package dev.zurbaevi.todolist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.zurbaevi.todolist.databinding.RowLayoutBinding
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.view.listener.OnItemClickListener
import java.text.DateFormat

class TaskAdapter : ListAdapter<TaskEntry, TaskAdapter.ViewHolder>(TaskDiffCallback) {

    private lateinit var clickListener: OnItemClickListener

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position), clickListener)
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<TaskEntry>() {
        override fun areItemsTheSame(oldItem: TaskEntry, newItem: TaskEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntry, newItem: TaskEntry) = oldItem == newItem
    }

    class ViewHolder private constructor(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(taskEntry: TaskEntry, clickListener: OnItemClickListener) = with(binding) {
            checkboxCompletedTask.text = taskEntry.title
            checkboxCompletedTask.isChecked = taskEntry.completed
            textRowTime.text = DateFormat.getInstance().format(taskEntry.timestamp)
            checkboxCompletedTask.setOnClickListener {
                clickListener.onCheckClick(
                    taskEntry.id,
                    taskEntry.title,
                    taskEntry.timestamp,
                    checkboxCompletedTask.isChecked
                )
            }
            itemView.setOnClickListener {
                clickListener.onItemClick(taskEntry)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    RowLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}
