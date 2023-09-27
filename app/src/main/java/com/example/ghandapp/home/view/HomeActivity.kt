package com.example.ghandapp.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.github.furkankaplan.fkblurview.FKBlurView

class HomeActivity: AppCompatActivity() {

    private lateinit var bindingActivity: ActivityHomeBinding
    private lateinit var binding: FornecedorListItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)



        val blurView = FKBlurView(binding as Context)
        blurView.setBlur(binding as Context, blurView, 20)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.fornecedor -> {
                true
            }
            R.id.list -> {
                true
            }
            R.id.registro -> {
                showRegisterScreen()
                true
            }
            R.id.calendarioOptions -> {
                true
            }
            R.id.despesa -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
            }
        }

        private fun showRegisterScreen() {
            startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
            finish()
        }

        private fun showFornecedorList(list: List<FornecedorModel>) {

        }
}

