package com.example.ghandapp.agenda.agendaProduto.data.repository

import android.util.Log
import android.view.View
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaToDelete
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaToFindModel
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaResponse
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaService

import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AgendaProdutoRepository {

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(AgendaService::class.java)

    suspend fun createDateInAgenda(username: String, name: String, cnpj: String, nameProduct: String, amount: Int, date: String, contextView: View): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.markADate(AgendaRequestModel(username, name, cnpj, nameProduct, amount, SituacaoProduto.NAO_RECEBIDO, date))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }

    suspend fun findAgenda(username: String, dateToPayOrReceive: String, contextView: View): List<AgendaProdutoModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findAgenda(AgendaToFindModel(username = username, dateToPayOrReceive = dateToPayOrReceive))
                if (response.isSuccessful) {
                    response.body()?.mapperAgenda() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }

    suspend fun listAgenda(username: String, contextView: View): List<AgendaProdutoModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findAgendas(username)
                if (response.isSuccessful) {
                    response.body()?.mapperAgenda() ?: emptyList()

                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }

    }
    suspend fun deleteAgenda(username: String,name: String,  cnpj: String, dateToPayOrReceive: String, contextView: View): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.deleteAgenda(username, name, cnpj, dateToPayOrReceive)
                response.isSuccessful
            } catch (exceptio: Exception) {
                Snackbar.make(contextView, exceptio.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    suspend fun updateStatus(username: String, name: String, cnpj: String, dateToPayOrReceive: String, contextView: View): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.changeStatus(username, name, cnpj, dateToPayOrReceive, SituacaoProduto.RECEBIDO)
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    private fun List<AgendaResponse>.mapperAgenda(): List<AgendaProdutoModel> {
        return map {
            it.agendaResponseToAgendaModel()
        }
    }

    private fun AgendaResponse.agendaResponseToAgendaModel(): AgendaProdutoModel {
        return AgendaProdutoModel(
            nameProduct = nameProduct,
            amount = amount,
            date = dateToPayOrReceive,
            situacaoProduto = status,
            cnpj = fornecedorDto.cnpj
        )
    }

}