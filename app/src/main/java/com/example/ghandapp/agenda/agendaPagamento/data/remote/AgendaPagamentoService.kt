package com.example.ghandapp.agenda.agendaPagamento.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AgendaPagamentoService {

    @POST("/agendaPagamento/setDateToPay")
    suspend fun setDateToPay(@Body agendaPagamentoRequest: AgendaPagamentoRequest): Response<ResponseBody>

    @GET()
    suspend fun getMarkedDate(@Body agendaPagamentoRequest: AgendaPagamentoRequest): Response<AgendaPagamentoResponse>

}