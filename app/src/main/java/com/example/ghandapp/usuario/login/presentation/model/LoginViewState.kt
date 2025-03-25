package com.example.ghandapp.usuario.login.presentation.model

sealed class LoginViewState {

    data class showIsSucess(val name: String) : LoginViewState()
    object usernameErrorMessage : LoginViewState()
    object loginInvalidMessage : LoginViewState()
    object passwordErrorMessage : LoginViewState()
    object genericErrorMessage : LoginViewState()
    object showLoading : LoginViewState()
    object showBlankInputs : LoginViewState()
}