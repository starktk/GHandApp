package com.example.ghandapp.fornecedor.data.domain

import com.example.ghandapp.fornecedor.data.remote.FornecedorModel
import com.example.ghandapp.fornecedor.data.repository.FornecedorRepository

class FornecedorUseCase {

    private val repositoryFornecedor by lazy { FornecedorRepository() }

    suspend fun createFornecedor(razaoSocial: String, cnpj: String, status: String, username: String): Boolean {
        return repositoryFornecedor.createFornecedor(razaoSocial, cnpj, status, username)
    }

    suspend fun findFornecedor(id: String): Boolean {
        return repositoryFornecedor.findFornecedor(id)
    }

    suspend fun deleteFornecedor(id: String): Boolean {
        return repositoryFornecedor.deleteFornecedor(id)
    }

    suspend fun modifyStatus(razaoSocial: String, status:String): Boolean {
        return repositoryFornecedor.modifyStatus(razaoSocial, status)
    }

    suspend fun getAllFornecedores(id: String): List<FornecedorModel> {
        return repositoryFornecedor.getAllFornecedores(id)
    }
}