package com.example.ghandapp.home.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProductActivity
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProdutoListAdapter
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate


class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val fornecedorAdapter by lazy {
        FornecedorListAdapter()
    }

    private val agendaAdapter by lazy {
        AgendaProdutoListAdapter()
    }

    private val viewModel: HomeViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent

        viewModel.initializer(intent.getStringExtra("stateStart").toString())
        initializeOberseve()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                is HomeViewState.showAgendaProdutoScreen -> showAgendaList(viewState.list)
                is HomeViewState.showFornecedorSingle -> showFornecedor(viewState.fornecedor)
                is HomeViewState.showAgendaPagamentoScreen -> TODO()
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
                HomeViewState.showEmptyAgenda -> showEmptyAgenda()
                HomeViewState.stateFornecedor -> bindForFornecedor()
                HomeViewState.changeStatus -> TODO()
                HomeViewState.showFailedMessage -> showFailMessage()
                HomeViewState.stateAgenda -> bindAgenda()
            }
        }
    }

    private fun showFailMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Procura inválida", Snackbar.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindAgenda() {
        binding.rvList.adapter = agendaAdapter
        binding.iconHome.setOnClickListener {
            finish()
        }
        binding.iconAdd.setOnClickListener {
            showRegisterAgendaScren()
        }
        binding.iconSearch.setOnClickListener {
            showAgendaDialog()
        }

    }

    private fun bindForFornecedor() {
        binding.rvList.adapter = fornecedorAdapter
        binding.iconHome.setOnClickListener {
           finish()
        }
        binding.iconAdd.setOnClickListener {
            showRegisterFornecedorScreen()
        }
        binding.iconSearch.setOnClickListener {
            showFornecedorDialog()
        }

    }

    private fun showRegisterAgendaScren() {
        startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
        finish()
    }

    private fun showFornecedor(fornecedorModel: FornecedorModel) {
        binding.pbLoading.hide()
        fornecedorAdapter.addSingleItem(fornecedorModel)
    }

    private fun showEmptyAgenda() {
        binding.pbLoading.hide()
    }

    private fun showAgendaList(list: List<AgendaProdutoModel>) {
        binding.pbLoading.hide()
        agendaAdapter.add(list)
    }


    private fun showFornecedorList(list: List<FornecedorModel>) {
        binding.pbLoading.hide()
        fornecedorAdapter.addAllItems(list)
    }

    private fun showRegisterFornecedorScreen() {
        startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
        finish()
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showEmptyList() {
        binding.pbLoading.hide()
    }

    private fun listar() {
        viewModel.listFornecedor()
    }

    private fun listarAgenda(cnpj: String, mes: String) {
        binding.rvList.adapter = agendaAdapter
        viewModel.listAgendaProduto(cnpj, mes)
    }
    private fun showFornecedorDialog() {
        val dialog = FornecedorDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val radioGroup: RadioGroup? = findViewById(R.id.rd_group)
        val btnSubmitFornecedor: Button? = findViewById(R.id.btn_submitFornecedor)
        radioGroup?.setOnCheckedChangeListener { _, checkId ->
                when (checkId) {
                R.id.btn_cnpj -> {
                    val cnpj: EditText = findViewById(R.id.etv_cnpj)
                    btnSubmitFornecedor?.setOnClickListener {
                        viewModel.findFornecedorByCnpj(cnpj.text.toString())
                    }
                }
                R.id.btn_razaoSocial -> {
                    val razaoSocial: EditText = findViewById(R.id.etv_razaoSocial)
                    btnSubmitFornecedor?.setOnClickListener {
                       viewModel.listByRazaoSocial(razaoSocial.text.toString())
                   }
                }
                R.id.btn_getAll -> {
                    btnSubmitFornecedor?.setOnClickListener {
                        listar()
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDateMonth(spinner: Spinner): LocalDate {
        val dateNow: LocalDate = LocalDate.now()
         when (spinner.selectedItem.toString()) {
            "Janeiro" -> dateNow.withMonth(1)
            "Fevereiro" -> dateNow.withMonth(2)
            "Março" -> dateNow.withMonth(3)
            "Abril" -> dateNow.withMonth(4)
            "Maio" -> dateNow.withMonth(5)
            "Junho" -> dateNow.withMonth(6)
            "Julho" -> dateNow.withMonth(7)
            "Agosto" -> dateNow.withMonth(8)
            "Setembro" -> dateNow.withMonth(9)
            "Outubro" -> dateNow.withMonth(10)
            "Novembro" -> dateNow.withMonth(11)
            "Dezembro" -> dateNow.withMonth(12)
        }
        return dateNow
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAgendaDialog() {
        val dialog = AgendaDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val text = R.id.btn_cnpj.toString()
        val rdGroup = findViewById<RadioGroup>(R.id.rd_group)
        val button = findViewById<Button>(R.id.btn_submit)
        rdGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.btn_pagamento -> button.setOnClickListener {

                }
            }
        }

        val spinner = findViewById<Spinner>(R.id.spinner_month)
        val month = setDateMonth(spinner)
        button.setOnClickListener {
            listarAgenda(
                cnpj = text,
                mes = month.toString()
            )
        }
    }
}

