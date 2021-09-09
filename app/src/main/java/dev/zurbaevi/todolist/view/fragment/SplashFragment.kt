package dev.zurbaevi.todolist.view.fragment

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

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.initSplashScreen()
        implementsObservers()
    }

    private fun implementsObservers() {
        splashViewModel.liveData.observe(viewLifecycleOwner, {
            findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToTaskFragment())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
