package com.example.ghandapp.register.registerUser.presentation.model

sealed class UsuarioViewState {

    object isCreated : UsuarioViewState()
    object genericErrorMessage : UsuarioViewState()
}