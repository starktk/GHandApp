package com.example.ghandapp.start

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.databinding.ActivityStartBinding
import com.example.ghandapp.home.presentation.model.StateStart
import com.example.ghandapp.home.view.HomeActivity

class StartActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun inicializateNavigation() {
        binding.btnFornecedor.setOnClickListener {
            startActivity(Intent(this@StartActivity, HomeActivity::class.java))
            HomeActivity(stateStart = StateStart.FORNECEDOR)
            finish()
        }
        binding.btnAgenda.setOnClickListener {
            startActivity(Intent(this@StartActivity, HomeActivity::class.java))
            HomeActivity(stateStart = StateStart.AGENDA)
            finish()
        }
    }
}