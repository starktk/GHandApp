package com.example.ghandapp.register.registerFornecedor.presentation.model

sealed class FornecedorViewState {

    object isCreated : FornecedorViewState()
    object genericErrorMessage : FornecedorViewState()
}
