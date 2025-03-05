package com.example.ghandapp.agenda.agendaProduto.data.domain

import android.view.View
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.data.repository.AgendaProdutoRepository
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class AgendaProdutoUseCase {

    private val repository by lazy {
        AgendaProdutoRepository()
    }
    private val loginRepo by lazy {
        LoginRepository()
    }

    suspend fun createAgenda(cnpj: String, nameProduct: String, amount: Int, date: String, contextView: View): Boolean {
        val user: UserEntity = loginRepo.getUser()
        return repository.createDateInAgenda(user.username, user.name, cnpj, nameProduct, amount, date, contextView)
    }
    suspend fun findAgendaByMonth(dateToPayOrReceive: String, contextView: View): List<AgendaProdutoModel> {
        return repository.findAgenda(loginRepo.getUser().username, dateToPayOrReceive, contextView)
    }
    suspend fun listAgenda(contextView: View): List<AgendaProdutoModel> {
        return repository.listAgenda(loginRepo.getUsername(), contextView)
    }
    suspend fun deleteAgenda(cnpj: String, dateToPayOrReceive: String, contextView: View): Boolean {
        return repository.deleteAgenda(loginRepo.getUser().username,loginRepo.getUser().name, cnpj, dateToPayOrReceive, contextView)
    }
    suspend fun modifyStatus(cnpj: String, dateToPayOrReceive: String, contextView: View): Boolean {
        return repository.updateStatus(loginRepo.getUsername(), loginRepo.getUser().name, cnpj, dateToPayOrReceive, contextView)
    }
}