package com.example.ghandapp.agenda.agendaProduto.data.domain

import android.view.View
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.data.repository.AgendaProdutoRepository
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
    suspend fun deleteAgenda(cnpj: String, dateToPayOrReceive: String) {
        return repository.deleteAgenda(loginRepo.getUser().username, cnpj, dateToPayOrReceive)
    }
}