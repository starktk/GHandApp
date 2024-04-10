package com.example.ghandapp.agenda.agendaPagamento.data.remote

import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento

data class AgendaPagamentoResponse(

    val dateToPayOrReceive: String? = null,
    val valueToPay: Double? = null,
    val situacaoPagamento: SituacaoPagamento? = null
)
