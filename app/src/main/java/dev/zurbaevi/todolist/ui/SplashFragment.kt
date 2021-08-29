package dev.zurbaevi.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.zurbaevi.todolist.databinding.FragmentSplashBinding
import dev.zurbaevi.todolist.util.safeNavigate
import dev.zurbaevi.todolist.viewmodel.SplashViewModel

class SplashFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(layoutInflater)

        observeSplashLiveData()

        return binding.root
    }

    private fun observeSplashLiveData() {
        splashViewModel.initSplashScreen()
        splashViewModel.liveData.observe(viewLifecycleOwner, {
            findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToTaskFragment())
        })
    }
}
