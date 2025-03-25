package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto

data class AgendaToFindModel (
    val username: String? = null,
    val cnpj: String? = null,
    val dateToPayOrReceive: String? = null,
    val status: SituacaoProduto? = null
        )