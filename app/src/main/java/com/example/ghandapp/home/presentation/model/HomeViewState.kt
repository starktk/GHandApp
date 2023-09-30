package com.example.ghandapp.home.presentation.model

import com.example.ghandapp.agenda.data.local.AgendaModel
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel

sealed class HomeViewState {

    data class showHomeScreen(val list: List<FornecedorModel>): HomeViewState()
    data class showAgendaScreen(val list: List<AgendaModel>): HomeViewState()
    object showLoading: HomeViewState()
    object showEmptyList: HomeViewState()

    object showEmptyAgenda: HomeViewState()

}
