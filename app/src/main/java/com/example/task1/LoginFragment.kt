package com.example.task1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.task1.databinding.FragmentLoginBinding
import com.example.task1.viewModel.LoginState
import com.example.task1.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var bind: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bind!!

    private val viewModel: LoginViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loginBefore()
            if (viewModel.state.value == LoginState.Success) {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(
                        R.id.action_loginFragment_to_homeFragment,
                        null,
                        navOptions { popUpTo(R.id.loginFragment) { inclusive = true } })
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_LONG
                ).show()
                LoginState.InProgress -> {}
                LoginState.Success ->
                    findNavController().navigate(
                        R.id.action_loginFragment_to_homeFragment,
                        null,
                        navOptions { popUpTo(R.id.loginFragment) { inclusive = true } })
            }
        }

        binding.loginViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}
