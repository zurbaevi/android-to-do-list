package dev.zurbaevi.todolist.view.fragment

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
import dev.zurbaevi.todolist.R
import dev.zurbaevi.todolist.databinding.FragmentUpdateTaskBottomSheetDialogBinding
import dev.zurbaevi.todolist.model.TaskEntry
import dev.zurbaevi.todolist.viewmodel.TaskViewModel

class UpdateTaskBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdateTaskBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateTaskBottomSheetDialogBinding.inflate(inflater, container, false)

        val args = UpdateTaskBottomSheetDialogFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            editUpdateTask.setText(args.taskEntry.title)

            buttonUpdateTask.setOnClickListener {
                if (TextUtils.isEmpty(editUpdateTask.text)) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toast_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.update(
                    TaskEntry(
                        args.taskEntry.id,
                        editUpdateTask.text.toString(),
                        args.taskEntry.timestamp,
                        args.taskEntry.completed
                    )
                )

                Toast.makeText(
                    requireContext(),
                    getString(R.string.toast_updated),
                    Toast.LENGTH_SHORT
                ).show()
            }

            return binding.root
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