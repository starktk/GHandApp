package com.example.ghandapp.register.registerUser.presentation.model

sealed class UsuarioViewState {

    object isCreated : UsuarioViewState()
    object genericErrorMessage : UsuarioViewState()
    object nameInvalidMessage : UsuarioViewState()
    object passwordInvalidMessage : UsuarioViewState()
    object showLoading : UsuarioViewState()

    object showInvalidInputs : UsuarioViewState()

    object badCreation : UsuarioViewState()


}