package com.example.ghandapp.agenda.data.repository

import com.example.ghandapp.agenda.data.local.AgendaModel
import com.example.ghandapp.agenda.data.local.AgendaRequestModel
import com.example.ghandapp.agenda.data.local.AgendaToFindModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AgendaService {

    @POST("/agendaproduct/agenda")
    suspend fun markADate(@Body agendaRequestModel: AgendaRequestModel): Response<ResponseBody>

    @GET("agendaproduct/findAgenda")
    suspend fun findAgenda(@Body agendaToFindModel: AgendaToFindModel): Response<AgendaModel>
}