package com.example.ghandapp.register.registerUser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghandapp.login.data.domain.LoginUseCase
import com.example.ghandapp.register.registerUser.presentation.model.UsuarioViewState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsuarioViewModel: ViewModel() {

    private val viewState = MutableLiveData<UsuarioViewState>()
    val state: LiveData<UsuarioViewState> = viewState

    private val usecase by lazy { LoginUseCase() }
    fun validateInputs(username: String, name: String, password: String) {



        viewState.value = UsuarioViewState.showLoading


        if (username.isNullOrBlank() && password.isNullOrBlank() && name.isNullOrBlank()) {
            viewState.value = UsuarioViewState.showInvalidInputs
            return
        }

        if (name.isNullOrBlank()) {
            viewState.value = UsuarioViewState.nameInvalidMessage
            return
        }

        if (password.isNullOrBlank()) {
            viewState.value = UsuarioViewState.passwordInvalidMessage
            return
        }

        fetchCreate(username = username, name = name, password = password)
    }

    private fun fetchCreate(username: String, name: String, password: String) {
        viewModelScope.launch {
            val iscreated = usecase.createUser(username, name, password)

            if (iscreated) {
                viewState.value = UsuarioViewState.isCreated
            } else {
                viewState.value = UsuarioViewState.badCreation
            }
        }
    }
}