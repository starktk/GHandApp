package com.example.ghandapp.agenda.agendaProduto.data.remote

import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaRequestModel
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaToDelete
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaResponse
import com.example.ghandapp.agenda.agendaProduto.data.remote.AgendaToFindModel
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


interface AgendaService {

    @POST("/agendaProduto/setDateToReceive")
    suspend fun markADate(@Body agendaRequestModel: AgendaRequestModel): Response<ResponseBody>

    @POST("agendaProduto/findByStatus")
    suspend fun findByStatus(@Body agendaToFindModel: AgendaToFindModel): Response<List<AgendaResponse>>
    @PUT("/agendaProduto/updateStatus")
    suspend fun changeStatus(@Query("username") username: String,
                             @Query("name") name: String,
                             @Query("cnpj") cnpj: String,
                             @Query("dateToPayOrReceive") dateToPayorReceive: String,
                             @Query("status") status: SituacaoProduto): Response<ResponseBody>

    @POST("agendaProduto/findAgendaByMonth")
    suspend fun findAgenda(@Body agendaToFindModel: AgendaToFindModel): Response<List<AgendaResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("agendaProduto/deleteReceive")
    suspend fun deleteAgenda(@Query("username") username: String,
                             @Query("name") name: String,
                             @Query("cnpj") cnpj: String,
                             @Query("dateToPayOrReceive") dateToPayorReceive: String): Response<ResponseBody>

    @POST("agendaProduto/findAgenda/{username}")
    suspend fun findAgendas(@Path("username") username: String): Response<List<AgendaResponse>>
}