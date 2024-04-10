package com.example.ghandapp.agenda.agendaProduto.presentation.model

sealed class AgendaProdutoViewState {
    object genericError : AgendaProdutoViewState()

    object cnpjErrorMessage : AgendaProdutoViewState()
    object dayToMarkError : AgendaProdutoViewState()
    object nameErrorMessage : AgendaProdutoViewState()
    object amountError : AgendaProdutoViewState()
    object showLoading: AgendaProdutoViewState()
    object showIsSucess: AgendaProdutoViewState()
    object badCreation: AgendaProdutoViewState()
}