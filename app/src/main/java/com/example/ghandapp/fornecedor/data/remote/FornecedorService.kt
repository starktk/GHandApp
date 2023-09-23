package com.example.ghandapp.fornecedor.data.remote

import com.example.ghandapp.fornecedor.data.local.FornecedorRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FornecedorService {

    @GET
    suspend fun findFornecedor(@Query("razaoSocial") razaoSocial: String): Response<FornecedorResponse>

    @POST
    suspend fun createFornecedor(@Body fornecedorRequest: FornecedorRequest)
}