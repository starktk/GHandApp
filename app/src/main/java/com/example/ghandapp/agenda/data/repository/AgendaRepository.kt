package com.example.ghandapp.agenda.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ghandapp.databinding.ActivityAgendaBinding
import com.example.ghandapp.agenda.data.local.AgendaToDelete
import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.agenda.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.data.remote.AgendaToFindModel
import com.example.ghandapp.agenda.data.local.SituacaoProduto
import com.example.ghandapp.agenda.data.remote.AgendaResponse
import com.example.ghandapp.agenda.presentation.model.AgendaViewState

import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AgendaRepository {

    private lateinit var binding: ActivityAgendaBinding

    private val viewState = MutableLiveData<AgendaViewState>()
    val state: LiveData<AgendaViewState> = viewState

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(AgendaService::class.java)

    suspend fun createDateInAgenda(username: String, name: String, cnpj: String, nameProduct: String, status: SituacaoProduto, amount: Int, date: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.markADate(AgendaRequestModel(username, name, cnpj, nameProduct, amount, status, date))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(binding.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }

    suspend fun findAgenda(username: String,cnpj: String, mes: String): List<AgendaModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findAgenda(AgendaToFindModel(username,cnpj, mes))
                if (response.isSuccessful) {
                    response.body()?.mapperAgenda() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Log.e("findAgenda", exception.message.orEmpty())
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
    private fun List<AgendaResponse>.mapperAgenda(): List<AgendaModel> {
        return map {
            it.agendaResponseToAgendaModel()
        }
    }

    private fun AgendaResponse.agendaResponseToAgendaModel(): AgendaModel {
        return AgendaModel(
            nameProduct = nameProduct,
            amount = amount,
            date = dateToPayOrReceive,
            status = status,
            fornecedorDto = fornecedorDto
        )
    }

}