package com.example.ghandapp.fornecedor.data.local

data class FornecedorRequest(
    val razaoSocial: String,
    val cnpj: String,
    val status: String,
    val username: String
)