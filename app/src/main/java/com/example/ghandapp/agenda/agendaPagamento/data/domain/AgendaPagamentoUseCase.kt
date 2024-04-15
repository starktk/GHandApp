package com.example.ghandapp.agenda.agendaPagamento.data.domain

import android.view.View
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.data.repository.AgendaPagamentoRepository
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class AgendaPagamentoUseCase {

    private val repository by lazy {
        AgendaPagamentoRepository()
    }

    private val loginRepo by lazy {
        LoginRepository()
    }

    suspend fun createDate(cnpj: String, valueToPay: Double, dateToPayOrReceive: String, contextView: View): Boolean {
        return repository.createDate(loginRepo.getUser().username, loginRepo.getUser().name, cnpj, valueToPay, dateToPayOrReceive, contextView)
    }

    suspend fun findAgendaByMonth(dateToPayOrReceive: String, contextView: View): List<AgendaPagamentoModel> {
        return repository.findByMonth(loginRepo.getUser().username, dateToPayOrReceive, contextView)
    }
}