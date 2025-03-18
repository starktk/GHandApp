package com.example.ghandapp.agenda.agendaPagamento.data.remote

import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse

data class AgendaPagamentoResponse(

    val dateToPayOrReceive: String,
    val valueToPay: Double,
    val situacaoPagamento: SituacaoPagamento,
    val fornecedorDto: FornecedorResponse
)
