package com.example.ghandapp.splashscreen.presentation.model

sealed class SplashScreenViewState {

    object showIsSucess: SplashScreenViewState()

    object showFailed: SplashScreenViewState()
}