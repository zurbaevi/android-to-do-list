package dev.zurbaevi.todolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.zurbaevi.todolist.data.model.TaskEntry
import dev.zurbaevi.todolist.databinding.RowLayoutBinding
import dev.zurbaevi.todolist.ui.listener.OnItemClickListener
import java.text.DateFormat

class TaskAdapter(private var clickListener: OnItemClickListener) :
    ListAdapter<TaskEntry, TaskAdapter.ViewHolder>(TaskDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<TaskEntry>() {
        override fun areItemsTheSame(oldItem: TaskEntry, newItem: TaskEntry) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntry, newItem: TaskEntry) = oldItem == newItem
    }

    inner class ViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(getItem(position))
                    }
                }
                checkboxCompletedTask.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onCheckBoxClick(
                            getItem(position),
                            checkboxCompletedTask.isChecked
                        )
                    }
                }
            }
        }

        fun binding(taskEntry: TaskEntry) = with(binding) {
            checkboxCompletedTask.isChecked = taskEntry.completed
            textViewTitle.text = taskEntry.title
            textViewTitle.paint.isStrikeThruText = taskEntry.completed
            textRowTime.text = DateFormat.getInstance().format(taskEntry.timestamp)
        }
    }

}
