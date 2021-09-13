package dev.zurbaevi.todolist.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dev.zurbaevi.todolist.R
import dev.zurbaevi.todolist.databinding.FragmentAddTaskBottomSheetDialogBinding
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.viewmodel.TaskViewModel
import dev.zurbaevi.todolist.viewmodel.TaskViewModelProviderFactory

class AddTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTaskBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelProviderFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonAddTask.setOnClickListener {
                if (TextUtils.isEmpty(editAddTitle.text)) {
                    DynamicToast.makeWarning(
                        requireContext(),
                        getString(R.string.dynamic_toast_empty)
                    ).show()
                    return@setOnClickListener
                }

                taskViewModel.insert(
                    TaskEntry(
                        0,
                        editAddTitle.text.toString(),
                        System.currentTimeMillis(),
                        false
                    )
                )

                DynamicToast.makeSuccess(requireContext(), getString(R.string.dynamic_toast_added))
                    .show()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}