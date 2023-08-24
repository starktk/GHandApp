package com.example.ghandapp.register.usuario.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghandapp.register.usuario.presentation.model.UsuarioViewState

class UsuarioViewModel: ViewModel() {

    private val viewState = MutableLiveData<UsuarioViewState>()
    val state: LiveData<UsuarioViewState> = viewState


}