package com.example.ghandapp.agenda.data.repository

import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.agenda.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.data.local.AgendaToDelete
import com.example.ghandapp.agenda.data.remote.AgendaResponse
import com.example.ghandapp.agenda.data.remote.AgendaToFindModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


interface AgendaService {

    @POST("/agendaProduto/setDateToReceive")
    suspend fun markADate(@Body agendaRequestModel: AgendaRequestModel): Response<ResponseBody>

    @GET("agendaProduto/findAgendaByMonth")
    suspend fun findAgenda(@Body agendaToFindModel: AgendaToFindModel): Response<List<AgendaResponse>>

    @DELETE("agendaProduto/deleteReceive")
    suspend fun deleteAgenda(@Body agendaToDelete: AgendaToDelete): Response<ResponseBody>
}