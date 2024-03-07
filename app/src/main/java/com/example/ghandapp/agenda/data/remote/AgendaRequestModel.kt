package com.example.ghandapp.agenda.data.remote

import com.example.ghandapp.agenda.data.local.SituacaoProduto

data class AgendaRequestModel(

    val username: String,
    val name: String,
    val cnpj: String,
    val nameProduct: String,
    val amount: Int,
    val status: SituacaoProduto,
    val date: String
)
