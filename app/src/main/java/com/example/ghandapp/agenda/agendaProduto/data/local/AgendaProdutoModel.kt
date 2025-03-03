package com.example.ghandapp.agenda.agendaProduto.data.local

import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgendaProdutoModel (

    val nameProduct: String,
    val amount: Int,
    val date: String,
    val situacaoProduto: SituacaoProduto,
    val cnpj: String
        )