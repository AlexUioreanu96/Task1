package com.example.task1.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.task1.MovieApplication
import com.example.task1.db.MovieRepository
import com.example.task1.models.StatusModel
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repo: MovieRepository) : ViewModel() {

    private var job: Job? = null

    val username = MutableLiveData("")


    val password = MutableLiveData("")

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() = _state


    suspend fun loginBefore(): LoginState {
        var stateLogin: LoginState
        stateLogin = LoginState.InProgress
        var tokenStatus: StatusModel? = null

        tokenStatus = repo.getStatusModel()

        if (tokenStatus != null) {
            stateLogin = LoginState.Success
        } else {
            stateLogin = LoginState.Error("Username is required or pass")
        }

        return stateLogin
    }


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

        job = viewModelScope.launch(Dispatchers.IO) {
            try {

                _state.postValue(LoginState.InProgress)

                var tokenStatus: StatusModel? = null

                tokenStatus = repo.getStatusModel()

                if (tokenStatus == null) {
                    val statusResponse: StatusModel = repo.login(usernameValue, passwordValue)
                    repo.insertToken(statusResponse)
                    if (statusResponse.success == true) {
                        _state.postValue(LoginState.Success)
                    } else {
                        _state.postValue(LoginState.Error("Username or password doesn't match"))
                    }
                } else {
                    _state.postValue(LoginState.Success)
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

sealed class LoginState {
    data class Error(val message: String) : LoginState()
    object Success : LoginState()
    object InProgress : LoginState()
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val application: MovieApplication
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(application.repository) as T
    }
}