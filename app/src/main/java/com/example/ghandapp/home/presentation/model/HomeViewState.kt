package com.example.ghandapp.home.presentation.model

sealed class HomeViewState {

    object showHomeScreen : HomeViewState()

    object showAgenda : HomeViewState()


}
