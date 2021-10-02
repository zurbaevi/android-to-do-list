package dev.zurbaevi.todolist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.zurbaevi.todolist.data.Constants.DATABASE_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = DATABASE_TABLE_NAME)
data class TaskEntry(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var timestamp: Long,
    var completed: Boolean,

    ) : Parcelable