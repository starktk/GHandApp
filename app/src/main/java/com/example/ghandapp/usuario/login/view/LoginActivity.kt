package com.example.ghandapp.usuario.login.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityLoginBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.usuario.login.presentation.LoginViewModel
import com.example.ghandapp.usuario.login.presentation.model.LoginViewState
import com.example.ghandapp.usuario.registerUser.view.UsuarioActivity
import com.google.android.material.snackbar.Snackbar


class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            viewModel.validateInputs(
                username = binding.username.text.toString(),
                password = binding.pwd.text.toString()
            )
        }

        binding.Register.setOnClickListener {
            showRegisterUser()
        }
        initializeObserver()
    }

    private fun initializeObserver() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                LoginViewState.showIsSucess -> showHome()
                LoginViewState.loginInvalidMessage -> invalidLogin()
                LoginViewState.showLoading -> showLoading()
                LoginViewState.usernameErrorMessage -> usernameErrorMesage()
                LoginViewState.passwordErrorMessage -> passwordErrorMessage()
                LoginViewState.showBlankInputs -> inputsBlank()
                LoginViewState.genericErrorMessage -> showGenericErrorMessage()
            }
        }
    }
    private fun showLoading() {
        binding.pbLoading.show()
    }


    private fun showGenericErrorMessage() {
        binding.root.hide()
        Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun inputsBlank() {

        binding.root.hide()
        Snackbar.make(binding.btLogin, R.string.blank_fields, Snackbar.LENGTH_SHORT).show()
    }

    private fun passwordErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.pwd, R.string.password_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun usernameErrorMesage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.username_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun invalidLogin() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.login_invalid, Snackbar.LENGTH_SHORT).show()
    }

    private fun showHome() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }

    private fun showRegisterUser() {
        startActivity(Intent(this@LoginActivity, UsuarioActivity::class.java))
        finish()
    }
}