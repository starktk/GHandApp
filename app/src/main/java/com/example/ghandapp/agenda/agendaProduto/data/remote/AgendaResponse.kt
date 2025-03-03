package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse

data class AgendaResponse(

    val nameProduct: String,
    val amount: Int,
    val status: SituacaoProduto,
    val dateToPayOrReceive: String,
    val fornecedorDto: FornecedorResponse

)
