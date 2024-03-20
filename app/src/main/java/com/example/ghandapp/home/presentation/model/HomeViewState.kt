package com.example.ghandapp.home.presentation.model

import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.fornecedor.data.model.FornecedorModel

sealed class HomeViewState {

    data class showHomeScreen(val list: List<FornecedorModel>): HomeViewState()
    data class showFornecedorSingle(val fornecedor: FornecedorModel): HomeViewState()
    data class showAgendaScreen(val list: List<AgendaModel>): HomeViewState()

    object stateFornecedor: HomeViewState()
    object stateAgenda: HomeViewState()
    object showLoading: HomeViewState()

    object changeStatus: HomeViewState()
    object showFailedMessage: HomeViewState()
    object showEmptyList: HomeViewState()
    object showEmptyAgenda: HomeViewState()

}
