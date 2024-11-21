package com.example.ghandapp.fornecedor.data.remote

import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FornecedorResponse(
    val razaoSocial: String,
    val cnpj: String,
    val status: Situacao
)
