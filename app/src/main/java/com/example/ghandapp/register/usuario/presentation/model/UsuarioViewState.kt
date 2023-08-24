package com.example.ghandapp.register.usuario.presentation.model

sealed class UsuarioViewState {

    object isCreated : UsuarioViewState()
    object genericErrorMessage : UsuarioViewState()
}