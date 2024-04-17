package com.example.ghandapp.fornecedor.data.domain

import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.data.repository.FornecedorRepository
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase

class FornecedorUseCase {

    private val repositoryFornecedor by lazy { FornecedorRepository() }

    private val loginUseCase by lazy {
        LoginUseCase()
    }
    suspend fun createFornecedor(razaoSocial: String, cnpj: String,): Boolean {
        return repositoryFornecedor.createFornecedor(razaoSocial, cnpj, loginUseCase.getUser().username, loginUseCase.getUser().name)
    }

    suspend fun findFornecedorByCnpj(cnpj: String): FornecedorModel? {
        return repositoryFornecedor.findFornecedorByCnpj(loginUseCase.getUser().username, cnpj)
    }

    suspend fun deleteFornecedor(cnpj: String): Boolean {
        return repositoryFornecedor.deleteFornecedor(loginUseCase.getUser().username, cnpj)
    }

    suspend fun modifyStatus(cnpj: String,status: Situacao): Boolean {
        return repositoryFornecedor.modifyStatus(loginUseCase.getUser().username, cnpj, status)
    }

    suspend fun getAllFornecedores(): List<FornecedorModel> {
        return repositoryFornecedor.getAllFornecedores(loginUseCase.getUser().username)
    }

    suspend fun findFornecedoresByRazaoSocial(razaoSocial: String): List<FornecedorModel> {
        return repositoryFornecedor.findByRazaoSocial(loginUseCase.getUser().username, razaoSocial)
    }

    suspend fun findFornecedoresByStatus(status: Situacao): List<FornecedorModel> {
        return repositoryFornecedor.findByStatus(loginUseCase.getUser().username, status)
    }

    suspend fun alterFornecedor(razaoSocial: String, cnpj: String, status: Situacao): FornecedorModel? {
        return repositoryFornecedor.alterFornecedor(loginUseCase.getUser().username, cnpj, razaoSocial, status, loginUseCase.getUser().name)
    }
}