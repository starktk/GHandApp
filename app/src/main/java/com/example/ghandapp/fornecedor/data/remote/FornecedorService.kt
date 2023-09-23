package com.example.ghandapp.fornecedor.data.remote

import com.example.ghandapp.fornecedor.data.local.FornecedorRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FornecedorService {

    @GET("fornecedor/getFornecedor/{id}")
    suspend fun findFornecedor(@Query("id") id: String): Response<FornecedorResponse>

    @POST("fornecedor/createFornecedor")
    suspend fun createFornecedor(@Body fornecedorRequest: FornecedorRequest): Response<ResponseBody>

    @PUT("fornecedor/alterFornecedor")
    suspend fun alterFornecedor(@Body fornecedorResponse: FornecedorResponse): Response<FornecedorResponse>

    @DELETE("fornecedor/deleteFornecedor/{id}")
    suspend fun deleteFornecedor(@Query("id") id: String): Response<ResponseBody>

    @GET("fornecedor/getAllFornecedores/{id}")
    suspend fun getAllFornecedores(@Query("id") id: String): Response<List<FornecedorResponse>>


}