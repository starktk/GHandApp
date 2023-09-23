package com.example.ghandapp.usuario.login.presentation.model

sealed class LoginViewState {

    object showIsSucess : LoginViewState()
    object usernameErrorMessage : LoginViewState()
    object loginInvalidMessage : LoginViewState()
    object passwordErrorMessage : LoginViewState()
    object genericErrorMessage : LoginViewState()
    object showLoading : LoginViewState()
    object showBlankInputs : LoginViewState()
}