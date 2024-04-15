package com.example.ghandapp.agenda.agendaPagamento.presentation.model

import com.example.ghandapp.agenda.agendaProduto.presentation.model.AgendaProdutoViewState

sealed class AgendaPagamentoViewState {

    object genericErrorMessage: AgendaPagamentoViewState()

    object showSucess: AgendaPagamentoViewState()

    object badCreation: AgendaPagamentoViewState()

    object cnpjErrorMessage: AgendaPagamentoViewState()

    object valueErrorMessage: AgendaPagamentoViewState()

    object dateErrorMessage: AgendaPagamentoViewState()

    object showLoading: AgendaPagamentoViewState()
}