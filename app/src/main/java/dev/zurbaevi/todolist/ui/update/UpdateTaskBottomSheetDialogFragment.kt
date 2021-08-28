package dev.zurbaevi.todolist.ui.update

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.zurbaevi.todolist.database.TaskEntry
import dev.zurbaevi.todolist.databinding.FragmentUpdateTaskBottomSheetDialogBinding
import dev.zurbaevi.todolist.viewmodel.TaskViewModel

class UpdateTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUpdateTaskBottomSheetDialogBinding.inflate(inflater)

        val args = UpdateTaskBottomSheetDialogFragmentArgs.fromBundle(requireArguments())

        binding.lifecycleOwner = this

        binding.apply {
            editFragmentUpdateEnterTask.setText(args.taskEntry.title)

            buttonFragmentUpdateTaskUpdate.setOnClickListener {
                if (TextUtils.isEmpty(editFragmentUpdateEnterTask.text)) {
                    Toast.makeText(requireContext(), "It's empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val taskEntry = TaskEntry(
                    args.taskEntry.id,
                    args.taskEntry.title,
                    args.taskEntry.timestamp
                )

                viewModel.update(taskEntry)
                Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
            }

            return binding.root
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }
}