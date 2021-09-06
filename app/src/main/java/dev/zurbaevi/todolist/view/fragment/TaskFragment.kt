package dev.zurbaevi.todolist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dev.zurbaevi.todolist.R
import dev.zurbaevi.todolist.databinding.FragmentTaskBinding
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.util.alertCompletedTasksDialog
import dev.zurbaevi.todolist.util.alertDeleteAllTasksDialog
import dev.zurbaevi.todolist.util.getCurrentDate
import dev.zurbaevi.todolist.util.safeNavigate
import dev.zurbaevi.todolist.view.adapter.TaskAdapter
import dev.zurbaevi.todolist.view.listener.OnItemClickListener
import dev.zurbaevi.todolist.viewmodel.TaskViewModel

class TaskFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        adapter = TaskAdapter(this)

        binding.textTodayTitle.text = getCurrentDate()

        viewModel.getAllTasks.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.textEmptyList.visibility = View.VISIBLE
            } else {
                binding.textEmptyList.visibility = View.GONE
            }
            adapter.submitList(it)
        })

        viewModel.getAllTasksCount.observe(viewLifecycleOwner, {
            binding.textTaskCount.text = String.format("$it %s", getString(R.string.tasks))
        })

        binding.apply {

            binding.recyclerView.adapter = adapter

            floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(object :
                FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
                override fun onMenuExpanded() {
                    binding.fabExpandMenuButtonAddTask.setOnClickListener {
                        findNavController().safeNavigate(TaskFragmentDirections.actionTaskFragmentToAddTaskBottomSheetDialogFragment())
                        floatingActionsMenu.collapse()
                    }
                    binding.fabExpandMenuButtonDeleteTasks.setOnClickListener {
                        alertDeleteAllTasksDialog(requireContext()) { viewModel.deleteAllTasks() }
                        floatingActionsMenu.collapse()
                    }

                    binding.fabExpandMenuButtonDeleteCompletedTasks.setOnClickListener {
                        alertCompletedTasksDialog(requireContext()) { viewModel.deleteCompletedTasks() }
                        floatingActionsMenu.collapse()
                    }
                }

                override fun onMenuCollapsed() {
                }
            })
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
                DynamicToast.makeSuccess(
                    requireContext(),
                    getString(R.string.dynamic_toast_deleted)
                ).show()
            }

        }).attachToRecyclerView(binding.recyclerView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(taskEntry: TaskEntry) {
        findNavController().safeNavigate(
            TaskFragmentDirections.actionTaskFragmentToUpdateTaskBottomSheetDialogFragment(
                taskEntry
            )
        )
    }

    override fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean) {
        viewModel.update(taskEntry, isChecked)
    }
}