package com.example.ghandapp.agenda.agendaPagamento.data.repository

import android.view.View
import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaPagamento.data.remote.AgendaPagamentoRequest
import com.example.ghandapp.agenda.agendaPagamento.data.remote.AgendaPagamentoService
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaService
import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AgendaPagamentoRepository {

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(AgendaPagamentoService::class.java)

    suspend fun createDate(username: String, name: String, cnpj: String, valueToPay: Double, contextView: View): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.setDateToPay(AgendaPagamentoRequest(username, name, cnpj, valueToPay, SituacaoPagamento.A_PAGAR))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
}