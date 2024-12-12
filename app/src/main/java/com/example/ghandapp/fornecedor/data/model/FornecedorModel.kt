package com.example.ghandapp.fornecedor.data.model

import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FornecedorModel(


    var razaoSocial: String? = null,
    var cnpj: String? = null,
    var status: Situacao? = null
)