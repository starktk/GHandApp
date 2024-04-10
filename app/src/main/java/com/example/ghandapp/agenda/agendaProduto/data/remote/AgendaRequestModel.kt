package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.agenda.agendaProduto.data.local.SituacaoProduto

data class AgendaRequestModel(

    val username: String,
    val name: String,
    val cnpj: String,
    val nameProduct: String,
    val amount: Int,
    val status: SituacaoProduto,
    val dateToPayOrReceive: String
)
