package com.example.ghandapp.agenda.agendaProduto.data.domain

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.data.repository.AgendaProdutoRepository
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.repository.LoginRepository
import java.time.LocalDate

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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun findAgendaByMonth(dateToPayOrReceive: Long, contextView: View): List<AgendaProdutoModel> {
        val date = LocalDate.now()
        val newDate = date.withMonth(dateToPayOrReceive.toInt())
        return repository.findAgenda(loginRepo.getUser().username, newDate.toString(), contextView)
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

    suspend fun findAgendaByStatus(status: SituacaoProduto, contextView: View): List<AgendaProdutoModel> {
        return repository.findAgendaByStatus(loginRepo.getUsername(), status, contextView)
    }
}