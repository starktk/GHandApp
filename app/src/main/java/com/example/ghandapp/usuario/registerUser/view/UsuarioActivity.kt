package com.example.ghandapp.usuario.registerUser.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityUsuarioBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.usuario.login.view.LoginActivity
import com.example.ghandapp.usuario.registerUser.presentation.UsuarioViewModel
import com.example.ghandapp.usuario.registerUser.presentation.model.UsuarioViewState
import com.google.android.material.snackbar.Snackbar


class UsuarioActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding

    private val viewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerUser.setOnClickListener {
            viewModel.validateInputs(
                username = binding.username.text.toString(),
                name = binding.nome.text.toString(),
                password = binding.pwd.text.toString()
            )
        }

        binding.accountAlreadyExist.setOnClickListener{
            showLogin()
        }

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
    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showGenericMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showNameInvalidMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.nome, R.string.name_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showInvalidInputs() {
        binding.pbLoading.hide()
        Snackbar.make(binding.registerUser, R.string.invalid_creation, Snackbar.LENGTH_SHORT).show()
    }

    private fun showPasswordInvalidMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.pwd, R.string.password_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showInvalidCreation() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.invalid_creation, Snackbar.LENGTH_SHORT).show()
    }
    private fun isCreated() {
        startActivity(Intent(this@UsuarioActivity, LoginActivity::class.java))
        finish()
    }

    private fun showLogin() {
        startActivity(Intent(this@UsuarioActivity, LoginActivity::class.java))
        finish()
    }
}