package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaToDelete
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaResponse
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaToFindModel
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