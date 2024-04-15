package com.example.ghandapp.agenda.agendaPagamento.data.remote

import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento

data class AgendaPagamentoRequest (
    val username: String? = null,
    val name: String? = null,
    val cnpj: String? = null,
    val valueToPay: Double? = null,
    val situacaoPagamento: SituacaoPagamento? = null,
    val dateToPayOrReceive: String? = null
)
