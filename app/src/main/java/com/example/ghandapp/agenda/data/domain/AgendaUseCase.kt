package com.example.ghandapp.agenda.data.domain

import com.example.ghandapp.agenda.data.repository.AgendaRepository

class AgendaUseCase {

    private val repository by lazy {
        AgendaRepository()
    }

    suspend fun createAgenda(razaoSocial: String, nameProduct: String, amount: Int, date: String): Boolean {
        return repository.createDateInAgenda(razaoSocial, nameProduct, amount, date)
    }

    suspend fun findAgenda(razaoSocial: String, mes: Int): Boolean {
        return repository.findAgenda(razaoSocial, mes)
    }
}