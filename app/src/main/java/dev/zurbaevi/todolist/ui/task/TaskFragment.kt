package dev.zurbaevi.todolist.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.zurbaevi.todolist.database.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentTaskBinding
import dev.zurbaevi.todolist.util.safeNavigate
import dev.zurbaevi.todolist.util.getCurrentDate
import dev.zurbaevi.todolist.viewmodel.TaskViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class TaskFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = TaskAdapter()
        adapter.setOnItemClickListener(object : TaskAdapter.OnItemClickListener {
            override fun onItemClick(taskEntry: TaskEntry) {
                findNavController().safeNavigate(
                    TaskFragmentDirections.actionTaskFragmentToUpdateTaskBottomSheetDialogFragment(
                        taskEntry
                    )
                )
            }
        })

        binding.textTodayTitle.text = getCurrentDate()

        viewModel.getAllTasks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.getAllTasksCount.observe(viewLifecycleOwner, {
            binding.textTaskCount.text = String.format("$it tasks")
        })

        binding.apply {

            binding.recyclerView.adapter = adapter

            floatingActionBar.setOnClickListener {
                findNavController().safeNavigate(TaskFragmentDirections.actionTaskFragmentToAddTaskBottomSheetDialogFragment())
            }
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntry = adapter.currentList[position]
                viewModel.delete(taskEntry)

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.insert(taskEntry)
                    }
                    show()
                }
            }

        }).attachToRecyclerView(binding.recyclerView)

        setHasOptionsMenu(true)

        return binding.root
    }
}