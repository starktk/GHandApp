package com.example.ghandapp.splashscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.splashscreen.presentation.model.SplashScreenViewState
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase
import com.example.ghandapp.usuario.login.data.local.UserEntity
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {

    private val viewState = MutableLiveData<SplashScreenViewState>()
    val state: LiveData<SplashScreenViewState> = viewState

    private val usecase by lazy {
        LoginUseCase()
    }

    init {
        viewModelScope.launch {
                val isSucess: UserEntity = usecase.getUser()
                if (isSucess != null) {
                    viewState.value = SplashScreenViewState.showIsSucess
                } else {
                    viewState.value = SplashScreenViewState.showFailed
                }
        }
    }
}