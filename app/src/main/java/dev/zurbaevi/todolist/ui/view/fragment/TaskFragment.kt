package dev.zurbaevi.todolist.ui.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.getbase.floatingactionbutton.FloatingActionsMenu
import dev.zurbaevi.todolist.R
import dev.zurbaevi.todolist.data.model.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentTaskBinding
import dev.zurbaevi.todolist.ui.adapter.TaskAdapter
import dev.zurbaevi.todolist.ui.viewmodel.TaskViewModel
import dev.zurbaevi.todolist.util.safeNavigate
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TaskFragment : Fragment(), TaskAdapter.OnItemClickListener {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModel()
    private var adapter: TaskAdapter = TaskAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        implementsObservers()
        implementsItemTouchHelper()

        binding.apply {

            textTodayTitle.text = SimpleDateFormat(
                "EEE, MMM d, yy",
                Locale.ENGLISH
            ).format(Calendar.getInstance().time)

            recyclerView.adapter = adapter

            floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(object :
                FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
                override fun onMenuExpanded() {
                    fabExpandMenuButtonAddTask.setOnClickListener {
                        findNavController().safeNavigate(TaskFragmentDirections.actionTaskFragmentToAddTaskBottomSheetDialogFragment())
                        floatingActionsMenu.collapse()
                    }
                    fabExpandMenuButtonDeleteTasks.setOnClickListener {
                        MaterialDialog(requireContext()).show {
                            title(R.string.alert_dialog_title)
                            message(R.string.alert_dialog_message)
                            positiveButton {
                                taskViewModel.deleteAllTasks()
                            }
                            negativeButton { dismiss() }
                        }
                        floatingActionsMenu.collapse()
                    }

                    fabExpandMenuButtonDeleteCompletedTasks.setOnClickListener {
                        MaterialDialog(requireContext()).show {
                            title(R.string.alert_dialog_completed_title)
                            message(R.string.alert_dialog_completed_message)
                            positiveButton {
                                taskViewModel.deleteCompletedTasks()
                            }
                            negativeButton { dismiss() }
                        }
                        floatingActionsMenu.collapse()
                    }
                }

                override fun onMenuCollapsed() {
                }
            })
        }
    }

    private fun implementsObservers() {
        taskViewModel.getAllTasks.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isEmpty()) {
                    binding.textEmptyList.visibility = View.VISIBLE
                } else {
                    binding.textEmptyList.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        })


        taskViewModel.getAllTasksCount.observe(viewLifecycleOwner, {
            binding.textTaskCount.text = String.format("$it %s", getString(R.string.tasks))
        })
    }

    private fun implementsItemTouchHelper() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntry = adapter.currentList[position]
                MaterialDialog(requireContext()).show {
                    title(R.string.alert_dialog_task_title)
                    message(R.string.alert_dialog_task_message)
                    positiveButton {
                        taskViewModel.delete(taskEntry)
                    }
                    negativeButton {
                        adapter.notifyItemChanged(position)
                        dismiss()
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addBackgroundColor(Color.RED)
                    .addActionIcon(R.drawable.ic_delete)
                    .setActionIconTint(Color.WHITE)
                    .create()
                    .decorate()
            }

        }).attachToRecyclerView(binding.recyclerView)
    }

    override fun onItemClick(taskEntry: TaskEntry) {
        findNavController().safeNavigate(
            TaskFragmentDirections.actionTaskFragmentToUpdateTaskBottomSheetDialogFragment(
                taskEntry
            )
        )
    }

    override fun onCheckBoxClick(taskEntry: TaskEntry, isChecked: Boolean) {
        taskViewModel.update(taskEntry, isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}