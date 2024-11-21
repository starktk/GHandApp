package com.example.ghandapp.usuario.login.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase
import com.example.ghandapp.usuario.login.presentation.model.LoginViewState
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val viewState = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> = viewState

    private val useCase by lazy { LoginUseCase() }

    fun validateInputs(username: String, password: String, contextView: View) {
        viewState.value = LoginViewState.showLoading

        if (username.isNullOrBlank() && password.isNullOrBlank()) {
            viewState.value = LoginViewState.showBlankInputs
            return
        }
        if (username.isNullOrBlank()) {
            viewState.value = LoginViewState.usernameErrorMessage
            return
        }
        if (password.isNullOrBlank()) {
            viewState.value = LoginViewState.passwordErrorMessage
            return
        }

        fetchLogin(contextView = contextView ,username = username, password = password)
    }


    private fun fetchLogin(contextView: View, username: String, password: String) {
        viewModelScope.launch {
            val isSucess = useCase.login(username, password, contextView)
            if(isSucess == true) {
                viewState.value = LoginViewState.showIsSucess
            } else {
                viewState.value = LoginViewState.loginInvalidMessage
            }
        }
    }

}