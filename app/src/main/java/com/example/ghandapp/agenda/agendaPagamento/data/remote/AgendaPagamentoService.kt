package com.example.ghandapp.agenda.agendaPagamento.data.remote

import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AgendaPagamentoService {

    @POST("/agendaPagamento/setDateToPay")
    suspend fun setDateToPay(@Body agendaPagamentoRequest: AgendaPagamentoRequest): Response<ResponseBody>

    @GET()
    suspend fun getMarkedDate(@Body agendaPagamentoRequest: AgendaPagamentoRequest): Response<List<AgendaPagamentoResponse>>
    @POST("agendaPagamento/listAgendas/{username}")
    suspend fun listAgendas(@Path("username") username: String): Response<List<AgendaPagamentoResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("/agendaPagamento/deletePayment")
    suspend fun deleteAgenda(@Query("username") username: String,
                             @Query("cnpj") cnpj: String,
                             @Query("dateToPayOrReceive") dateToPayOrReceive: String): Response<ResponseBody>

    @PUT("/agendaProduto/updateStatus")
    suspend fun modifyStatus(@Query("username") username: String,
                             @Query("name") name: String,
                             @Query("cnpj") cnpj: String,
                             @Query("dateToPayOrReceive") dateToPayorReceive: String,
                             @Query("status") status: SituacaoPagamento
    ): Response<ResponseBody>
}