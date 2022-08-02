package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.task1.databinding.FragmentLoginBinding
import com.example.task1.models.StatusModel
import com.example.task1.models.UserModel
import com.example.task1.retrofit.LoginClientRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class LoginFragment : Fragment() {

    private var bind: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = LoginClientRetrofit()


        var user = UserModel()

        user.username = bind?.loginEmail.toString()
        user.password = bind?.loginPass.toString()

        lifecycleScope.async(Dispatchers.IO) {
            var status: StatusModel = StatusModel()

            //Request Token
            status = retrofit.retrieveRequestToken()

            //Login
            user.requestToken = status.requestToken
            status = retrofit.login(user)

            //sessionId
            status = retrofit.postSessionId(user.requestToken.toString())

            //userDetailsA
            user = retrofit.getUserDetails(status.sessionId.toString())

            //invalidate session
            status.success = retrofit.invalidateSession(status.sessionId.toString())

            println(status)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

}