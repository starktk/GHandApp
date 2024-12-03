package com.example.ghandapp.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.databinding.ActivityStartBinding
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.home.view.HomeActivity

class StartActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicializateNavigation()
    }

    private fun inicializateNavigation() {
        binding.btnFornecedor.setOnClickListener {
            val intent = (Intent(this@StartActivity, HomeActivity::class.java))
            intent.putExtra("stateStart", StateStart.FORNECEDOR.toString())
            startActivity(intent)
            finish()
        }
        binding.btnAgenda.setOnClickListener {
            val intent = (Intent(this@StartActivity, HomeActivity::class.java))
            intent.putExtra("stateStart", StateStart.AGENDA.toString())
            startActivity(intent)
            finish()
        }
    }
}
