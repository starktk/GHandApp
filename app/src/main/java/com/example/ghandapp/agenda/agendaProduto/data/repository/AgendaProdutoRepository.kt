package com.example.ghandapp.agenda.agendaProduto.data.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaToDelete
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaToFindModel
import com.example.ghandapp.agenda.agendaProduto.data.local.SituacaoProduto
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaResponse
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaService
import com.example.ghandapp.agenda.agendaProduto.presentation.model.AgendaProdutoViewState

import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    suspend fun deleteAgenda(username: String, cnpj: String, dateToPayOrReceive: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.deleteAgenda(AgendaToDelete(username, cnpj, dateToPayOrReceive))
                response.isSuccessful
            } catch (exceptio: java.lang.Exception) {
                Log.e("delete", exceptio.message.orEmpty())

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
            status = status,
        )
    }

}