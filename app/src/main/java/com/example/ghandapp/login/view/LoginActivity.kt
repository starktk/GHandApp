package com.example.ghandapp.login.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityLoginBinding
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.login.presentation.LoginViewModel
import com.example.ghandapp.login.presentation.model.LoginViewState
import com.example.ghandapp.register.registerUser.view.UsuarioActivity

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

    private fun showGenericErrorMessage() {

    }

    private fun inputsBlank() {
        TODO("Not yet implemented")
    }

    private fun passwordErrorMessage() {

    }

    private fun usernameErrorMesage() {
    }

    private fun invalidLogin() {
        TODO("Not yet implemented")
    }

    private fun showHome() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
    private fun showLoading() {

    }

    private fun showRegisterUser() {
        startActivity(Intent(this@LoginActivity, UsuarioActivity::class.java))
        finish()
    }
}