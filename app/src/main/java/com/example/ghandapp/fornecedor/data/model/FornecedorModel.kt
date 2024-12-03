package com.example.ghandapp.fornecedor.data.model

import com.example.ghandapp.fornecedor.presentation.enums.Situacao

data class FornecedorModel(


    val razaoSocial: String? = null,
    val cnpj: String? = null,
    var status: Situacao? = null
)