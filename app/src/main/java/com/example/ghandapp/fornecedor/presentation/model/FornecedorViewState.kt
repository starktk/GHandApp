package com.example.ghandapp.fornecedor.presentation.model

sealed class FornecedorViewState {

    object isCreated : FornecedorViewState()
    object genericErrorMessage : FornecedorViewState()
    object razaoSocialErrorMessage : FornecedorViewState()
    object cnpjErrorMessage: FornecedorViewState()
    object badCreation: FornecedorViewState()
    object missingUsernameReference: FornecedorViewState()
    object showLoading: FornecedorViewState()

    object blankOrEmptyInputs: FornecedorViewState()
}
