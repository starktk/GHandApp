package com.example.ghandapp.agenda.presentation.model

sealed class AgendaViewState {
    object genericError : AgendaViewState()
    object dayToMarkError : AgendaViewState()
    object nameErrorMessage : AgendaViewState()
    object amountError : AgendaViewState()
    object razaoSocialErrorMessage : AgendaViewState()
    object showLoading: AgendaViewState()
    object showIsSucess: AgendaViewState()
    object badCreation: AgendaViewState()
}