package com.example.ghandapp.register.registerUser.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.databinding.ActivityUsuarioBinding
import com.example.ghandapp.login.view.LoginActivity
import com.example.ghandapp.register.registerUser.presentation.UsuarioViewModel
import com.example.ghandapp.register.registerUser.presentation.model.UsuarioViewState


class UsuarioActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding

    private val viewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeObserver()


    }

    private fun initializeObserver() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                UsuarioViewState.isCreated -> isCreated()
                UsuarioViewState.genericErrorMessage -> showGenericMessage()
                UsuarioViewState.nameInvalidMessage -> showNameInvalidMessage()
                UsuarioViewState.showInvalidInputs -> showInvalidInputs()
                UsuarioViewState.showLoading -> showLoading()
                UsuarioViewState.passwordInvalidMessage -> showPasswordInvalidMessage()
                UsuarioViewState.badCreation -> showInvalidCreation()
            }
        }
    }

    private fun isCreated() {
        startActivity(Intent(this@UsuarioActivity, LoginActivity::class.java))
        finish()
    }

    private fun showGenericMessage() {
        TODO("Not yet implemented")
    }

    private fun showNameInvalidMessage() {
        TODO("Not yet implemented")
    }

    private fun showInvalidInputs() {
        TODO("Not yet implemented")
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun showPasswordInvalidMessage() {
        TODO("Not yet implemented")
    }

    private fun showInvalidCreation() {
        TODO("Not yet implemented")
    }
}