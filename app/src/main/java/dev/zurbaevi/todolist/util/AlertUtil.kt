package dev.zurbaevi.todolist.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dev.zurbaevi.todolist.R

fun alertDeleteAllTasksDialog(context: Context, yes: (DialogInterface) -> Unit = {}) =
    AlertDialog.Builder(context).setTitle(context.getString(R.string.alert_dialog_title))
        .setMessage(context.getString(R.string.alert_dialog_message))
        .setPositiveButton(context.getString(R.string.alert_dialog_positive_button)) { dialog: DialogInterface, _: Int ->
            yes.invoke(dialog)
            dialog.dismiss()
            DynamicToast.makeSuccess(context, context.getString(R.string.dynamic_toast_deleted))
                .show()
        }
        .setNegativeButton(context.getString(R.string.alert_dialog_negative_button)) { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }.create().show()


fun alertCompletedTasksDialog(context: Context, yes: (DialogInterface) -> Unit = {}) =
    AlertDialog.Builder(context).setTitle(context.getString(R.string.alert_dialog_completed_title))
        .setMessage(context.getString(R.string.alert_dialog_completed_message))
        .setPositiveButton(context.getString(R.string.alert_dialog_positive_button)) { dialog: DialogInterface, _: Int ->
            yes.invoke(dialog)
            dialog.dismiss()
            DynamicToast.makeSuccess(context, context.getString(R.string.dynamic_toast_deleted))
                .show()
        }
        .setNegativeButton(context.getString(R.string.alert_dialog_negative_button)) { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }.create().show()
