package com.example.ghandapp.fornecedor.data.repository

import android.util.Log
import com.example.ghandapp.database.FHdatabase
import com.example.ghandapp.fornecedor.data.local.FornecedorEntitiy
import com.example.ghandapp.fornecedor.data.local.FornecedorRequest
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel
import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse
import com.example.ghandapp.fornecedor.data.remote.FornecedorService
import com.example.ghandapp.network.RetrofitNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class FornecedorRepository {

    private val database: FHdatabase by lazy {
        FHdatabase.getInstance()
    }

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(FornecedorService::class.java)

    suspend fun createFornecedor(razaoSocial: String, cnpj: String, status: String, username: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.createFornecedor(FornecedorRequest(razaoSocial, cnpj, status, username))
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("create", exception.message.orEmpty())
                false
            }
        }
    }
    suspend fun findFornecedor(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedorFind = client.findFornecedor(id = id)
                saveFornecedor(fornecedorFind)
                fornecedorFind.isSuccessful
            } catch (exception: Exception) {
                Log.e("find Fornecedor", exception.message.orEmpty())
                false
            }
        }
    }

    suspend fun cleanDBFornecedor() {
        return withContext(Dispatchers.IO) {
            database.fornecedorDao().cleanFornecedor()
        }
    }

    suspend fun getAllFornecedores(id: String): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedoresReturn = client.getAllFornecedores(id)
                if(fornecedoresReturn.isSuccessful) {
                    fornecedoresReturn.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Log.e("findFornecedor", exception.message.orEmpty())
                emptyList()
            }
        }
    }

    suspend fun alterFornecedor() {

    }

    suspend fun modifyStatus(razaoSocial: String, status: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.alterStatus(razaoSocial = razaoSocial, status = status)
                saveFornecedor(response)
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("change Modify", exception.message.orEmpty())
                false
            }
        }
    }
    suspend fun deleteFornecedor(id: String): Boolean {
        return withContext(Dispatchers.IO) {
           try {
               val response = client.deleteFornecedor(id)
               response.isSuccessful
           } catch (exception: Exception) {
               Log.e("Deletar Fornecedor", exception.message.orEmpty())
               false
           }
        }
    }
    private suspend fun saveFornecedor(fornecedor: Response<FornecedorResponse>) {
        return withContext(Dispatchers.IO) {
            if (fornecedor.isSuccessful) {
                fornecedor.body()?.run {
                    database.fornecedorDao().insertFornecedor(
                        fornecedorResponseToEntity()
                    )
                }
            }
        }
    }

    private fun List<FornecedorResponse>.mapperFornecedor(): List<FornecedorModel> {
        return map {
            it.fornecedorResponseToFornecedorModel()
        }
    }

    private fun FornecedorResponse.fornecedorResponseToFornecedorModel(): FornecedorModel {
        return FornecedorModel(
            razaoSocial = razaoSocial,
            cnpj = cnpj,
            status = status
        )
    }

    private fun FornecedorResponse.fornecedorResponseToEntity(): FornecedorEntitiy {
        return FornecedorEntitiy(
            razaoSocial = razaoSocial,
            cnpj = cnpj,
            status = status
        )
    }
}