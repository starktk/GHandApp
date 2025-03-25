package com.example.ghandapp.agenda.agendaPagamento.data.domain

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaPagamento.data.repository.AgendaPagamentoRepository
import com.example.ghandapp.usuario.login.data.repository.LoginRepository
import java.time.LocalDate

class AgendaPagamentoUseCase {

    private val repository by lazy {
        AgendaPagamentoRepository()
    }

    private val loginRepo by lazy {
        LoginRepository()
    }

    suspend fun listAgenda(contextView: View): List<AgendaPagamentoModel> {
        return repository.listAgendas(contextView, loginRepo.getUsername())
    }
    suspend fun createDate(cnpj: String, valueToPay: Double, dateToPayOrReceive: String, contextView: View): Boolean {
        return repository.createDate(loginRepo.getUser().username, loginRepo.getUser().name, cnpj, valueToPay, dateToPayOrReceive, contextView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun findAgendaByMonth(dateToPayOrReceive: Long, contextView: View): List<AgendaPagamentoModel> {
        val date = LocalDate.now()
        val newDate = date.withMonth(dateToPayOrReceive.toInt())
        return repository.findByMonth(loginRepo.getUser().username, newDate.toString(), contextView)
    }

    suspend fun deleteAgenda(contextView: View, cnpj: String, dateToPayOrReceive: String): Boolean {
        return repository.deleteAgenda(loginRepo.getUsername(),cnpj, dateToPayOrReceive, contextView)
    }

    suspend fun modifyStatus(cnpj: String, dateToPayOrReceive: String, contextView: View): Boolean {
        return repository.modifyStatus(loginRepo.getUsername(), loginRepo.getUser().name, cnpj, dateToPayOrReceive, contextView)
    }

    suspend fun listByStatus(status: SituacaoPagamento, contextView: View): List<AgendaPagamentoModel> {
        return repository.listAgendaByStatus(loginRepo.getUsername(), status, contextView)
    }
}