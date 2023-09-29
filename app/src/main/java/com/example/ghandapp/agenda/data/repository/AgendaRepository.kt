package com.example.ghandapp.agenda.data.repository

import android.util.Log
import com.example.ghandapp.agenda.data.local.AgendaRequestModel
import com.example.ghandapp.agenda.data.local.AgendaToFindModel
import com.example.ghandapp.network.RetrofitNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AgendaRepository {

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(AgendaService::class.java)

    suspend fun createDateInAgenda(razaoSocial: String, nameProduct: String, amount: Int, date: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.markADate(AgendaRequestModel(razaoSocial, nameProduct, amount, date))
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("create", exception.message.orEmpty())
                false
            }
        }
    }

    suspend fun findAgenda(razaoSocial: String, mes: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findAgenda(AgendaToFindModel(razaoSocial, mes))
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("create", exception.message.orEmpty())
                false
            }
        }
    }

}