package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse

data class AgendaResponse(

    val nameProduct: String,
    val amount: Int,
    val status: String,
    val dateToPayOrReceive: String,
    val fornecedorDto: FornecedorResponse
)
