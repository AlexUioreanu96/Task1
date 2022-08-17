package com.example.task1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.models.LoginRequest
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    private var job: Job? = null
    val username = MutableLiveData("")


    val password = MutableLiveData("")

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() = _state


    fun login() {
        val retrofit = LoginRepository()

        val usernameValue = username.value
        val passwordValue = password.value

        if (usernameValue.isNullOrBlank()) {
            _state.value = LoginState.Error("Username is required")
            return
        }
        if (passwordValue.isNullOrBlank()) {
            _state.value = LoginState.Error("Password is required")
            return
        }


        job?.cancel()

        job = viewModelScope.launch {
            delay(3000)
            try {
                _state.postValue(LoginState.InProgress)

                //Request Token
                val statusRetrive = retrofit.retrieveRequestToken()

                val loginRequest = LoginRequest(
                    username = usernameValue,
                    password = passwordValue,
                    requestToken = statusRetrive.requestToken
                )

                val statusLogin = retrofit.login(loginRequest)

                if (statusLogin.success == true) {
                    _state.postValue(LoginState.Success)
                } else {
                    _state.postValue(LoginState.Error("Username or password doesn't match"))
                }
            } catch (e: IOException) {
                _state.postValue(LoginState.Error("Network error, please try again"))
                Log.w("loginViewModel", "Error while login", e)
            } catch (e: HttpException) {
                _state.postValue(LoginState.Error("Username or password doesn't match"))
                Log.w("loginViewModel", "Error while login", e)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}

sealed class LoginState() {
    data class Error(val message: String) : LoginState()
    object Success : LoginState()
    object InProgress : LoginState()
}