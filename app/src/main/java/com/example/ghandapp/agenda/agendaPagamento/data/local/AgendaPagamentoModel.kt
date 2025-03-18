package com.example.ghandapp.agenda.agendaPagamento.data.local

data class AgendaPagamentoModel(

    val valueToPay: Double,
    val dateToPayOrReceive: String,
    val status: String,
    val cnpj: String
)
