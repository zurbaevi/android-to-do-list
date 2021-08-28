package dev.zurbaevi.todolist.ui.add

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.zurbaevi.todolist.database.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentAddTaskBottomSheetDialogBinding
import dev.zurbaevi.todolist.util.safeNavigate
import dev.zurbaevi.todolist.viewmodel.TaskViewModel

class AddTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddTaskBottomSheetDialogBinding.inflate(inflater)

        binding.apply {
            buttonFragmentAddTaskAdd.setOnClickListener {
                if (TextUtils.isEmpty(editFragmentAddEnterTask.text)) {
                    Toast.makeText(requireContext(), "It's empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val taskEntry = TaskEntry(
                    0,
                    editFragmentAddEnterTask.text.toString(),
                    System.currentTimeMillis()
                )

                viewModel.insert(taskEntry)
                Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }
}