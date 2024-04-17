package com.example.ghandapp.fornecedor.data.repository

import com.example.ghandapp.database.FHdatabase
import com.example.ghandapp.databinding.ActivityFornecedorBinding
import com.example.ghandapp.fornecedor.data.local.FornecedorEntitiy
import com.example.ghandapp.fornecedor.data.remote.FornecedorRequest
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse
import com.example.ghandapp.fornecedor.data.remote.FornecedorService
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FornecedorRepository {

    private lateinit var bindingFornecedor: ActivityFornecedorBinding


    private val database: FHdatabase by lazy {
        FHdatabase.getInstance()
    }

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(FornecedorService::class.java)

    suspend fun createFornecedor(razaoSocial: String, cnpj: String, username: String, name: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.createFornecedor(FornecedorRequest(razaoSocial = razaoSocial, cnpj = cnpj, status = Situacao.Ativa , username = username, name = name))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    suspend fun findFornecedorByCnpj(username: String, cnpj: String): FornecedorModel? {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedorFind = client.findFornecedorByCnpj(FornecedorRequest(username = username, cnpj = cnpj))
                if (fornecedorFind.isSuccessful) {
                    fornecedorFind.body().fornecedorResponseToFornecedorModel()
                } else {
                    null
                }
            } catch (exception: Exception) {
               Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                null
            }
        }
    }

    suspend fun cleanDBFornecedor() {
        return withContext(Dispatchers.IO) {
            database.fornecedorDao().cleanFornecedor()
        }
    }

    suspend fun getAllFornecedores(username: String): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedoresReturn = client.getAllFornecedores(username = username)
                if(fornecedoresReturn.isSuccessful) {
                    fornecedoresReturn.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
               Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }

    suspend fun alterFornecedor(username: String, cnpj: String, razaoSocial: String, status: Situacao, name: String): FornecedorModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.alterFornecedor(FornecedorRequest(razaoSocial = razaoSocial, cnpj = cnpj, status = status, username = username, name = name))
                if (response.isSuccessful) {
                    response.body().fornecedorResponseToFornecedorModel()
                } else {
                    null
                }
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                null
            }
        }
    }


    suspend fun modifyStatus(username: String, cnpj: String, status: Situacao): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.alterStatus(FornecedorRequest(username = username, cnpj = cnpj, status = status))
                saveFornecedor(response)
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    suspend fun deleteFornecedor(username: String, cnpj: String): Boolean {
        return withContext(Dispatchers.IO) {
           try {
               val response = client.deleteFornecedor(FornecedorRequest(username = username, cnpj = cnpj))
               response.isSuccessful
           } catch (exception: Exception) {
               Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
               false
           }
        }
    }

    suspend fun findByRazaoSocial(username: String, razaoSocial: String): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findFornecedoresByRazaoSocial(FornecedorRequest(razaoSocial = razaoSocial, username = username))
                if (response.isSuccessful) {
                    response.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }
    suspend fun findByStatus(username: String, status: Situacao): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findByStatus(FornecedorRequest(username = username , status = status))
                if (response.isSuccessful) {
                    response.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
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

    private fun FornecedorResponse?.fornecedorResponseToFornecedorModel(): FornecedorModel {
        return FornecedorModel(
            razaoSocial = this?.razaoSocial,
            cnpj = this?.cnpj,
            status = this?.status?.toString()
        )
    }

    private fun FornecedorResponse.fornecedorResponseToEntity(): FornecedorEntitiy {
        return FornecedorEntitiy(
            razaoSocial = razaoSocial,
            cnpj = cnpj,
            status = status.toString()
        )
    }
}