package dev.zurbaevi.todolist.ui.task

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.google.android.material.snackbar.Snackbar
import dev.zurbaevi.todolist.database.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentTaskBinding
import dev.zurbaevi.todolist.util.getCurrentDate
import dev.zurbaevi.todolist.util.safeNavigate
import dev.zurbaevi.todolist.viewmodel.TaskViewModel

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

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

            floatingActionMenu.setOnFloatingActionsMenuUpdateListener(object :
                FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
                override fun onMenuExpanded() {
                    binding.fabExpandMenuButtonAddTask.setOnClickListener {
                        findNavController().safeNavigate(TaskFragmentDirections.actionTaskFragmentToAddTaskBottomSheetDialogFragment())
                    }
                    binding.fabExpandMenuButtonAddDeleteTask.setOnClickListener {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Delete all tasks")
                            .setMessage("Are you, sure you want to delete all tasks?")
                            .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                                viewModel.deleteAllTasks()
                                dialog.dismiss()
                                Toast.makeText(requireContext(), "Deleted!", Toast.LENGTH_SHORT)
                                    .show()
                            }.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                                dialog.dismiss()
                            }
                            .create().show()
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

                Snackbar.make(binding.root, "Deleted!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.insert(taskEntry)
                    }
                    show()
                }
            }

        }).attachToRecyclerView(binding.recyclerView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}