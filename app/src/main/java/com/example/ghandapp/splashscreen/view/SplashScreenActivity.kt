package com.example.ghandapp.splashscreen.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.databinding.ActivitySplashscreenBinding
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.splashscreen.presentation.SplashScreenViewModel
import com.example.ghandapp.splashscreen.presentation.model.SplashScreenViewState
import com.example.ghandapp.usuario.login.view.LoginActivity


class SplashScreenActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel.validateLog()
        initializeObserve()
    }
    private fun initializeObserve() {
        viewModel.state.observe(this) {viewState -> when(viewState) {
            SplashScreenViewState.showIsSucess -> showIsSucess()
            SplashScreenViewState.showFailed -> showFailed()
            }
        }
    }

    private fun showIsSucess() {
        startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
        finish()
    }

    private fun showFailed() {
        startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
        finish()
    }
}