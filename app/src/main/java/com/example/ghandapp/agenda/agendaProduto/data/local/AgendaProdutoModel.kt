package com.example.ghandapp.agenda.agendaProduto.data.local

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgendaProdutoModel (

    val nameProduct: String,
    val amount: Int,
    val date: String,
    val status: String,
        )