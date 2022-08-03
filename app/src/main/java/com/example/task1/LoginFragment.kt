package com.example.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.task1.databinding.FragmentLoginBinding
import com.example.task1.models.StatusModel
import com.example.task1.models.UserModel
import com.example.task1.retrofit.LoginClientRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {


    private var bind: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = LoginClientRetrofit()
        val fragmentHome = HomeFragment()
        var user = UserModel()
        var statusRetrive: StatusModel = StatusModel()
        var statusLogin: StatusModel = StatusModel()

        binding.loginButton.setOnClickListener {

            user.username = binding.loginEmail.editText?.text.toString()
            user.password = binding.loginPass.editText?.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    //Request Token
                    statusRetrive = retrofit.retrieveRequestToken()

                    //Login
                    user.requestToken = statusRetrive.requestToken
                    println("adsads")
                    statusLogin = retrofit.login(user)
                    println("dasdas")

                    if (statusLogin.success == true) {
                        parentFragmentManager.beginTransaction()
                            .replace<HomeFragment>(R.id.fragment_container_view_tag)
                            .commit()
                    }
                } catch (e: Exception) {
                }
            }
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
}
