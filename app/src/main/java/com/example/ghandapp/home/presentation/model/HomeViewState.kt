package com.example.ghandapp.home.presentation.model

import com.example.ghandapp.fornecedor.data.remote.FornecedorModel

sealed class HomeViewState {

    data class showHomeScreen(val list: List<FornecedorModel>): HomeViewState()

    object showLoading: HomeViewState()

    object showEmptyList: HomeViewState()


}
