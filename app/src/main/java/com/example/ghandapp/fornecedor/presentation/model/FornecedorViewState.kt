package com.example.ghandapp.fornecedor.presentation.model

sealed class FornecedorViewState {

    object isCreated : FornecedorViewState()
    object genericErrorMessage : FornecedorViewState()
}
