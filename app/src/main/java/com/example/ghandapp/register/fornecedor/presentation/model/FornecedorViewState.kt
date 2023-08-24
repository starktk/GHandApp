package com.example.ghandapp.register.fornecedor.presentation.model

sealed class FornecedorViewState {

    object isCreated : FornecedorViewState()
    object genericErrorMessage : FornecedorViewState()
}
