package com.example.ghandapp.agenda.presentation.model

sealed class AgendaViewState {
    object changeStatus : AgendaViewState()
    object genericError : AgendaViewState()
    object dayToMarkError : AgendaViewState()
    object dateFutureIvalid : AgendaViewState()
    object nameErrorMessage : AgendaViewState()
    object amountError : AgendaViewState()
    object razaoSocialErrorMessage : AgendaViewState()
    object updateHistorico : AgendaViewState()
}