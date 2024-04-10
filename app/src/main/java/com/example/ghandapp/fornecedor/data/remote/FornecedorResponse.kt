package com.example.ghandapp.fornecedor.data.remote

import com.example.ghandapp.agenda.agendaProduto.data.local.SituacaoProduto

data class FornecedorResponse(
    val razaoSocial: String,
    val cnpj: String,
    val status: SituacaoProduto
)
