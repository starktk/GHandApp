package com.example.ghandapp.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoActivity
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoListAdapter
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
import com.example.ghandapp.home.presentation.enums.StatusSearch
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.start.StartActivity
import com.example.ghandapp.usuario.login.view.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate


class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val fornecedorAdapter by lazy {
        FornecedorListAdapter(
            onStatusChange = { fornecedor ->
                viewModel.modifyStatus(
                    fornecedor.cnpj.toString(),
                    fornecedor.status.toString().uppercase()
            )
        },
            onEditChange = { fornecedor, cnpj ->
                if (cnpj != null) {
                    viewModel.updateFornecedor(fornecedor, cnpj, binding.root)
                }
            }
        )

    }

    private val agendaProdutoAdapter by lazy {
        AgendaProdutoListAdapter()
    }
    private val agendaPagamentoAdapter by lazy {
        AgendaPagamentoListAdapter()
    }
    
    private val viewModel: HomeViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refresh()
        observeEvents()
        init(binding.root)
        initializeOberseve()
        setupItemTouchHelper(binding.rvList)
    }
    private fun init(contextView: View) {
        viewModel.initializer(intent.getStringExtra("stateStart").toString(), contextView)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                is HomeViewState.showAgendaProdutoScreen -> showAgendaProdutoList(viewState.list)
                is HomeViewState.showFornecedorSingle -> showFornecedor(viewState.fornecedor)
                is HomeViewState.showAgendaPagamentoScreen -> showAgendaPagamentoList(viewState.list)
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
                HomeViewState.showEmptyAgenda -> showEmptyAgenda()
                HomeViewState.stateFornecedor -> bindForFornecedor()
                HomeViewState.changeStatus -> showMessageStatus()
                HomeViewState.showFailedMessage -> showFailMessage()
                HomeViewState.stateAgenda -> bindAgenda()
                HomeViewState.showFailedStatusMessage -> showFailedStatusMessage()
                HomeViewState.showFailedUpdateMessage -> showFailedUpdateMessage()
                HomeViewState.showFailedMessageToDelete -> showFailedMessageToDelete()
                HomeViewState.showSucessDeletedMessage -> showSucessDeletedMessage()
            }
        }
    }
    private fun refresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.listFornecedoresRefresh(binding.root)
            binding.swipeRefreshLayout.postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
    }
    private fun showFailedMessageToDelete() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi possivel deletar", Snackbar.LENGTH_LONG).show()
    }

    private fun showSucessDeletedMessage() {
        binding.pbLoading.hide()
        val emoji = "✅"
        Snackbar.make(binding.root, emoji, Snackbar.LENGTH_LONG).show()
    }

    private fun setupItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder ):
                        Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    println(fornecedorAdapter.getObjectInListByPosition(position))
                    viewModel.deleteFornecedor(fornecedorAdapter.getObjectInListByPosition(position).cnpj, binding.root)
                    fornecedorAdapter.removeItem(position)
                }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun showFailedUpdateMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Edição inválida", Snackbar.LENGTH_LONG).show()
    }

    private fun showFailedStatusMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi possivel modificar o status", Snackbar.LENGTH_LONG).show()
    }

    private fun showMessageStatus() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Status modificado com sucesso", Snackbar.LENGTH_LONG).show()
    }


    private fun observeEvents() {
        binding.iconHome.setOnClickListener {
            startActivity(Intent(this@HomeActivity, StartActivity::class.java))
            finish()
        }

        binding.iconProfile.setOnClickListener{
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun showAgendaPagamentoList(list: List<AgendaPagamentoModel>) {
        binding.pbLoading.hide()
        agendaPagamentoAdapter.add(list)
    }

    private fun showFailMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Procura inválida", Snackbar.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindAgenda() {
        binding.iconHome.setOnClickListener {
            finish()
        }
        binding.iconAdd.setOnClickListener {
            showMiddleChoiceDIalog()
        }
        binding.iconSearch.setOnClickListener {
            showAgendaDialog()
        }

    }

    @SuppressLint("ResourceType")
    private fun bindForFornecedor() {
        binding.rvList.adapter = fornecedorAdapter
        binding.iconHome.setOnClickListener {
            finish()
        }
        binding.iconAdd.setOnClickListener {
            showRegisterFornecedorScreen()
        }
        binding.iconSearch.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            Snackbar.make(binding.root, "Não faz nada ainda", Snackbar.LENGTH_SHORT).show()

            //          menuInflater.inflate(R.menu.menu_search, popupMenu.menu)

//            popupMenu.show()
//            val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.selectopt)
//            popupMenu.setOnMenuItemClickListener { menuItem ->
//                menuItem.actionView?.startAnimation(scaleAnim)
//                when (menuItem.itemId) {
//                    R.id.item_razao_social -> {
//                        showFornecedorDialog(StatusSearch.RAZAO_SOCIAL)
//                        true
//                    }
//                    R.id.item_cnpj -> {
//                        // Ação para CNPJ
//                        Toast.makeText(this, "CNPJ selecionado", Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                    R.id.item_status -> {
//                        // Ação para Status
//                        Toast.makeText(this, "Status selecionado", Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                    else -> false
//                }
//            }
        }
    }

    private fun showRegisterAgendaProdutoScren() {
        startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
        finish()
    }
    private fun showRegisterAgendaPagamentoScreen() {
        startActivity(Intent(this@HomeActivity, AgendaPagamentoActivity::class.java))
        finish()
    }

    private fun showFornecedor(fornecedorModel: FornecedorModel) {
        binding.pbLoading.hide()
        fornecedorAdapter.addSingleItem(fornecedorModel)
    }

    private fun showEmptyAgenda() {
        binding.pbLoading.hide()
    }

    private fun showAgendaProdutoList(list: List<AgendaProdutoModel>) {
        binding.pbLoading.hide()
        agendaProdutoAdapter.add(list)
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


    private fun listarAgendaProduto(mes: String) {
        binding.rvList.adapter = agendaProdutoAdapter
        viewModel.listAgendaProduto(mes, binding.root)
    }
    private fun showFornecedorDialog(status: StatusSearch) {
        val dialog = FornecedorDialogFragment.newInstance()
        dialog.show(supportFragmentManager, "fornecedorDialog")
        dialog.onSubmitClick = { input ->
            when (status) {
                StatusSearch.RAZAO_SOCIAL -> {
                        viewModel.listByRazaoSocial(input, binding.root)
                }
                StatusSearch.CNPJ -> {
                    viewModel.findFornecedorByCnpj(input)
                }
                StatusSearch.STATUS -> {

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

    private fun showMiddleChoiceDIalog() {
        val dialog = MiddleDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val rdGroup = findViewById<RadioGroup>(R.id.rd_groupMiddle)
        rdGroup.setOnCheckedChangeListener { _, checkId ->
            when(checkId) {
                R.id.btn_pagamentoMiddle -> showRegisterAgendaPagamentoScreen()
                R.id.btn_produtoMiddle -> showRegisterAgendaProdutoScren()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAgendaDialog() {
        val dialog = AgendaDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val rdGroup = findViewById<RadioGroup>(R.id.rd_group)
        val button = findViewById<Button>(R.id.btn_submit)
        rdGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.btn_pagamento -> button.setOnClickListener {

                }
                R.id.btn_produto -> button.setOnClickListener {
                    val spinner = findViewById<Spinner>(R.id.spinner_month)
                    val month = setDateMonth(spinner)
                    button.setOnClickListener {
                        listarAgendaProduto(
                            mes = month.toString()
                        )
                    }
                }
            }
        }


    }
}

