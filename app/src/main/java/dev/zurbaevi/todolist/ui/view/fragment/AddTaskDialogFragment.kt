package dev.zurbaevi.todolist.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.zurbaevi.todolist.R
import dev.zurbaevi.todolist.data.model.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentAddTaskDialogBinding
import dev.zurbaevi.todolist.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTaskDialogBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddTaskDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonAddTask.setOnClickListener {
                if (TextUtils.isEmpty(editAddTitle.text)) {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_empty,
                        Toast.LENGTH_LONG
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

                dialog?.dismiss()
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