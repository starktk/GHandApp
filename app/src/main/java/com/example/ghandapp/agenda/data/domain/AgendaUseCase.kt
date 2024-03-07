package com.example.ghandapp.agenda.data.domain

import com.example.ghandapp.agenda.data.local.SituacaoProduto
import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.agenda.data.repository.AgendaRepository
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class AgendaUseCase {

    private val repository by lazy {
        AgendaRepository()
    }
    private val loginRepo by lazy {
        LoginRepository()
    }

    suspend fun createAgenda(cnpj: String, nameProduct: String, amount: Int, date: String): Boolean {
        val user: UserEntity = loginRepo.getUser()
        return repository.createDateInAgenda(user.username, user.name, cnpj, nameProduct, SituacaoProduto.NAO_RECEBIDO, amount, date)
    }
    suspend fun findAgendaByMonth(cnpj: String, mes: String): List<AgendaModel> {
        return repository.findAgenda(loginRepo.getUser().username, cnpj, mes)
    }
    suspend fun deleteAgenda(cnpj: String, dateToPayOrReceive: String) {
        return repository.deleteAgenda(loginRepo.getUser().username, cnpj, dateToPayOrReceive)
    }
}