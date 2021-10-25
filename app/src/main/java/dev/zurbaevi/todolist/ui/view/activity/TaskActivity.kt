package dev.zurbaevi.todolist.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.zurbaevi.todolist.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}