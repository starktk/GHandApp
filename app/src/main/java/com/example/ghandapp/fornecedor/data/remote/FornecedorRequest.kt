package com.example.ghandapp.fornecedor.data.remote

import com.example.ghandapp.fornecedor.presentation.enums.Situacao

data class FornecedorRequest(
    val razaoSocial: String? = null,
    val cnpj: String? = null,
    val status: Situacao? = null,
    val username: String? = null,
    val name: String? = null
)
