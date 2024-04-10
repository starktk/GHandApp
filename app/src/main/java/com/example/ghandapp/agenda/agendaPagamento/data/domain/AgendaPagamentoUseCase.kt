package com.example.ghandapp.agenda.agendaPagamento.data.domain

import android.view.View
import com.example.ghandapp.agenda.agendaPagamento.data.repository.AgendaPagamentoRepository
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class AgendaPagamentoUseCase {

    private val repository by lazy {
        AgendaPagamentoRepository()
    }

    private val loginRepo by lazy {
        LoginRepository()
    }

    suspend fun createDate(cnpj: String, valueToPay: Double, contextView: View): Boolean {
        return repository.createDate(loginRepo.getUser().username, loginRepo.getUser().name, cnpj, valueToPay, contextView)
    }

    fun findAgendaByMonth(cnpj: String, mes: String): List<AgendaPagamentoModel> {

    }
}