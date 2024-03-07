package com.example.ghandapp.agenda.data.remote

import com.example.ghandapp.fornecedor.data.model.FornecedorResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgendaModel (

    val nameProduct: String,
    val amount: Int,
    val date: String,
    val status: String,
    val fornecedorDto: FornecedorResponse
        )