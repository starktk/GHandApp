package com.example.ghandapp.agenda.data.remote

import com.example.ghandapp.fornecedor.data.model.FornecedorResponse

data class AgendaResponse(

    val nameProduct: String,
    val amount: Int,
    val status: String,
    val dateToPayOrReceive: String,
    val fornecedorDto: FornecedorResponse
)
