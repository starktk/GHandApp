package com.example.ghandapp.splashscreen.presentation.model

sealed class SplashScreenViewState {

    data class showIsSucess(val name: String): SplashScreenViewState()

    object showFailed: SplashScreenViewState()
}