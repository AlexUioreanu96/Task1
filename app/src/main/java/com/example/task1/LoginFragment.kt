package com.example.task1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.task1.databinding.FragmentLoginBinding
import com.example.task1.models.StatusModel
import com.example.task1.models.UserModel
import com.example.task1.retrofit.LoginRepository
import com.example.task1.viewModel.LoginState
import com.example.task1.viewModel.LoginViewModel


class LoginFragment : Fragment() {


    private var bind: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bind!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = LoginRepository()
        val user = UserModel()
        var statusRetrive = StatusModel()
        var statusLogin = StatusModel()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.loginButton.setOnClickListener {

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
                    parentFragmentManager.beginTransaction()
                        .replace<HomeFragment>(R.id.fragment_container_view_tag)
                        .commit()
            }
        }

        binding.loginViewModel = viewModel

//        viewModel.updateUsername()
        viewModel.username.observe(viewLifecycleOwner) { newUserName ->
            binding.loginEmail.editText?.setText(newUserName)
            Log.w("loginss", binding.loginEmail.editText?.setText(newUserName).toString())
        }

        binding.loginEmail.editText?.setText(viewModel.username.value)


        viewModel.password.observe(viewLifecycleOwner) { newPass ->
            binding.loginPass.editText?.setText(newPass)
            Log.w("loginss", binding.loginPass.editText?.setText(newPass).toString())
        }


//            println("sgsa")
//            //sessionId
//            status = retrofit.postSessionId(user.requestToken.toString())
//
//            //userDetailsA
//            user = retrofit.getUserDetails(status.sessionId.toString())
//
//            //invalidate session
//            status.success = retrofit.invalidateSession(status.sessionId.toString())
//
//            println(status)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
//        viewModel.updatePass(binding.loginEmail.editText?.text.toString())
//        viewModel.updateUsername(binding.loginPass.editText?.text.toString())
//
//        Log.w("loginss", viewModel.username.plus(binding.loginEmail.editText?.text.toString()))
//        Log.w("loginss", viewModel.password.plus(binding.loginPass.editText?.text.toString()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
