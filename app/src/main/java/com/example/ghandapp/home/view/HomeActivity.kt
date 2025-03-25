package com.example.ghandapp.home.view

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoActivity
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoListAdapter
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProductActivity
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProdutoListAdapter
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.view.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate


class HomeActivity: AppCompatActivity() {
    var contextScreen: StateStart = StateStart.FORNECEDOR
    private lateinit var binding: ActivityHomeBinding
    private var contextMonthFilter: Boolean = false
    private var contextStatus: Boolean = false
    private val fornecedorAdapter by lazy {
        FornecedorListAdapter(
            onStatusChange = { fornecedor ->
                viewModel.modifyStatusFornecedor(
                    fornecedor.cnpj.toString(),
                    fornecedor.status.toString().uppercase()
            )
        },
            onEditChange = { fornecedor, cnpj ->
                if (cnpj != null) {
                    viewModel.updateFornecedor(fornecedor, cnpj, binding.root)
                    viewModel.listFornecedoresRefresh(binding.root)
                }
            },
            sendToWhatsapp = {fornecedor ->
                viewModel.sendToWhatsapp(fornecedor.contactNumber.toString())
            }
        )

    }

    private val agendaProdutoAdapter by lazy {
        AgendaProdutoListAdapter(
            onStatusChange = {
                agendaProduto -> viewModel.modifyStatusAgendaProduto(agendaProduto.situacaoProduto, agendaProduto.cnpj, agendaProduto.date, binding.root)
                viewModel.listAgendaProdutos(binding.root)
            }
        )
    }
    private val agendaPagamentoAdapter by lazy {
        AgendaPagamentoListAdapter(
            onStatusChange = {
                agendaPagamento -> viewModel.modifyStatusAgendaPagamento(agendaPagamento.status, agendaPagamento.cnpj, agendaPagamento.dateToPayOrReceive, binding.root)
            }
        )
    }

    private val viewModel: HomeViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val typeScreen = intent?.getSerializableExtra("stateStart") as? StateStart
        val name = intent.getStringExtra("userNAME")
        binding.name.text = name
        if (typeScreen == null || typeScreen == StateStart.FORNECEDOR) {
            viewModel.listFornecedoresRefresh(binding.root)
        }
        startActivity(typeScreen)
        refresh()
        observeEvents()
        init()
        initializeOberseve()
        setupItemTouchHelper(binding.rvList)
    }

    private fun startActivity(typeScreen: StateStart?) {
        if (typeScreen != null) {
            when (typeScreen) {
                StateStart.FORNECEDOR -> viewModel.initializer(typeScreen, binding.root)
                StateStart.AGENDAPROD -> viewModel.initializer(typeScreen, binding.root)
                StateStart.AGENDAPAG -> viewModel.initializer(typeScreen, binding.root)

            }
        } else {
            contextScreen = StateStart.FORNECEDOR
            viewModel.initializer(contextScreen, binding.root)
        }
    }
    private fun init() {
        binding.fornecedorScreen.setOnClickListener {
            contextScreen = StateStart.FORNECEDOR
            binding.rvList.adapter = fornecedorAdapter
            viewModel.initializer(contextScreen, binding.root)
        }
        binding.agendaScreen.setOnClickListener {
            contextScreen = StateStart.AGENDAPROD
            binding.rvList.adapter = agendaProdutoAdapter
            viewModel.initializer(contextScreen, binding.root)
        }
        binding.agendaScreenPayment.setOnClickListener {
            contextScreen = StateStart.AGENDAPAG
            binding.rvList.adapter = agendaPagamentoAdapter
            viewModel.initializer(contextScreen, binding.root)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                is HomeViewState.showAgendaProdutoScreen -> showAgendaProdutoList(viewState.list)
                is HomeViewState.showFornecedorSingle -> showFornecedor(viewState.fornecedor)
                is HomeViewState.showAgendaPagamentoScreen -> showAgendaPagamentoList(viewState.list)
                is HomeViewState.sucessUser -> showNameForFinalUser(viewState.name)
                is HomeViewState.showProfile -> showProfile(viewState.user)
                is HomeViewState.showSucessUserEdited -> showNewUserSettings(viewState.user)
                is HomeViewState.showWhatsapp -> intentToWhatsapp(viewState.contactNumber)
                HomeViewState.numberErrorMessage -> showMessageErrorForContactNumber()
                HomeViewState.showFailedMessageUpdateUser -> showFailUpdateMessage()
                HomeViewState.showFailedUser -> userFail()
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
                HomeViewState.showEmptyAgenda -> showEmptyAgenda()
                HomeViewState.stateFornecedor -> bindForFornecedor()
                HomeViewState.stateAgenda -> bindAgenda()
                HomeViewState.stateAgendaPayment -> bindAgendaPayment()
                HomeViewState.changeStatus -> showMessageStatus()
                HomeViewState.showFailedMessage -> showFailMessage()
                HomeViewState.showFailedStatusMessage -> showFailedStatusMessage()
                HomeViewState.showFailedUpdateMessage -> showFailedUpdateMessage()
                HomeViewState.showFailedMessageToDelete -> showFailedMessageToDelete()
                HomeViewState.showSucessDeletedMessage -> showSucessDeletedMessage()
            }
        }
    }

    private fun showMessageErrorForContactNumber() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Número inválido", Snackbar.LENGTH_LONG).show()
    }

    private fun intentToWhatsapp(contactNumber: String) {
        binding.pbLoading.hide()
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$contactNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
            try {
                startActivity(playStoreIntent)
            } catch (ex: ActivityNotFoundException) {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"))
                startActivity(webIntent)
            }
        }
    }


    private fun userFail() {
        Snackbar.make(binding.root, "Faça Login novamente", Snackbar.LENGTH_SHORT).show()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
    }

    private fun showNameForFinalUser(name: String) {
        binding.name.text = name
    }

    private fun refresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            when (contextScreen) {
                StateStart.FORNECEDOR -> {
                    viewModel.listFornecedoresRefresh(binding.root)
                    binding.swipeRefreshLayout.postDelayed({
                        binding.swipeRefreshLayout.isRefreshing = false
                    }, 2000)
                }
                StateStart.AGENDAPROD -> {
                    binding.monthFilter.setSelection(0)
                    viewModel.listAgendaProdutos(binding.root)
                    binding.swipeRefreshLayout.postDelayed({
                        binding.swipeRefreshLayout.isRefreshing = false
                    }, 2000)
                }
                else -> {
                    binding.monthFilter.setSelection(0)
                    viewModel.listAgendaPayment(binding.root)
                    binding.swipeRefreshLayout.postDelayed({
                        binding.swipeRefreshLayout.isRefreshing = false
                    }, 2000)
                }
            }
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
                    if (contextScreen == StateStart.FORNECEDOR) {
                        viewModel.deleteFornecedor(fornecedorAdapter.getObjectInListByPosition(position).cnpj, binding.root)
                        fornecedorAdapter.removeItem(position)
                    } else if (contextScreen == StateStart.AGENDAPROD) {
                        viewModel.deleteAgenda(agendaProdutoAdapter.getObjectInListByPosition(position).cnpj, agendaProdutoAdapter.getObjectInListByPosition(position).date, binding.root)
                        agendaProdutoAdapter.removeItem(position)
                    } else {
                        viewModel.deleteAgendaPag(binding.root, agendaPagamentoAdapter.getObjectInListByPosition(position).cnpj, agendaPagamentoAdapter.getObjectInListByPosition(position).dateToPayOrReceive)
                        agendaPagamentoAdapter.removeItem(position)
                    }
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
//        binding.pbLoading.hide()
//        Snackbar.make(binding.root, "Status modificado com sucesso", Snackbar.LENGTH_LONG).show()
    }

    private fun swapAdapterWithManualSlide(newAdapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.animate().translationX(recyclerView.width.toFloat()).setDuration(200).withEndAction {
            recyclerView.adapter = newAdapter
            recyclerView.translationX = -recyclerView.width.toFloat()
            recyclerView.animate().translationX(0f).setDuration(200).start()
        }.start()
    }
    private fun observeEvents() {
        binding.iconProfile.setOnClickListener{
            openDrawer()
        }
    }

    private fun showProfile(user: UserEntity) {
        controlVisibility(true)
        binding.edtNameProfile.setText(user.name)
        binding.edtName.setText(user.username)
        binding.editProfile.setOnClickListener {
            editProfile(true)
            binding.editProfile.setBackgroundResource(R.drawable.ic_action_conclusive)
            binding.editProfile.setOnClickListener {
                editUser( binding.edtName.text.toString(), binding.edtNameProfile.text.toString(), binding.edtPassword.text.toString())
            }
        }
        binding.closeProfile.setOnClickListener {
            editProfile(false)
            controlVisibility(false)
        }

    }
    private fun showFailUpdateMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi possivel editar o Usuario", Snackbar.LENGTH_LONG).show()
    }

    private fun showNewUserSettings(user: UserEntity) {
        binding.pbLoading.hide()
        binding.edtNameProfile.setText(user.name)
        binding.edtName.setText(user.username)
        binding.name.text = user.name
    }
    private fun editUser(username: String, name: String, password: String) {
        editProfile(false)
        binding.editProfile.setBackgroundResource(R.drawable.edit_icon)
        viewModel.editUser(username, name, password, binding.root)
    }
    private fun controlVisibility(state: Boolean) {
        if (state) {
            binding.menuOption1.visibility = View.GONE
            binding.menuOption2.visibility = View.GONE
            binding.cardview.visibility = View.VISIBLE
        } else {
            binding.cardview.visibility = View.GONE
            binding.menuOption1.visibility = View.VISIBLE
            binding.menuOption2.visibility = View.VISIBLE
        }
    }
    private fun editProfile(state: Boolean) {
        if (state) {
            binding.edtNameProfile.isClickable = true
            binding.edtNameProfile.isFocusable = true
            binding.edtNameProfile.isFocusableInTouchMode = true
            binding.edtName.isFocusable = true
            binding.edtName.isFocusableInTouchMode = true
            binding.edtName.isClickable = true

            binding.edtPassword.visibility = View.VISIBLE
        } else {
            binding.edtNameProfile.isClickable = false
            binding.edtNameProfile.isFocusable = false
            binding.edtNameProfile.isFocusableInTouchMode = false
            binding.edtName.isFocusable = false
            binding.edtName.isFocusableInTouchMode = false
            binding.edtName.isClickable = false
            binding.edtPassword.visibility = View.GONE
        }
    }
    private fun openDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawerLayout.openDrawer(GravityCompat.START)
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                binding.menuOption1.setOnClickListener {
                    viewModel.showProfile()
                }
                binding.menuOption2.setOnClickListener {
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                binding.edtName.setText("")
                binding.edtNameProfile.setText("")
                controlVisibility(false)
                binding.pbLoading.hide()
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
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
        binding.rvList.adapter = agendaProdutoAdapter
        swapAdapterWithManualSlide(binding.rvList.adapter as AgendaProdutoListAdapter, binding.rvList)
        binding.searchMenu.visibility = View.GONE
        binding.searchMenuAgenda.visibility = View.VISIBLE
        if (contextMonthFilter) {
            binding.monthFilter.setSelection(0)
        }
        if (contextStatus) {
            binding.spinnerStatusAgenda.setSelection(0)
        }
        val resourceFilterStatus = resources.getStringArray(R.array.status_agendaProd)
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resourceFilterStatus)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStatusAgenda.adapter = statusAdapter
        val swipeParams = binding.swipeRefreshLayout.layoutParams as ConstraintLayout.LayoutParams
        swipeParams.topToBottom = binding.searchMenuAgenda.id
        binding.swipeRefreshLayout.layoutParams = swipeParams
        binding.iconAddsScreenContext.setOnClickListener {
            if (contextScreen == StateStart.AGENDAPROD) {
                startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
            }
        }
        binding.spinnerStatusAgenda.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                contextStatus = false
                when (itemSelecionado) {
                    "Status" -> {}
                    "Recebido" -> {
                        contextStatus = true
                        viewModel.listAgendaProdByStatus(SituacaoProduto.RECEBIDO, binding.root)
                    }

                    "Não Recebido" -> {
                        contextStatus = true
                        viewModel.listAgendaProdByStatus(SituacaoProduto.NAO_RECEBIDO, binding.root)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.monthFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                contextMonthFilter = false
                when (itemSelecionado) {
                    "Selecione a opção" -> {}
                    "Janeiro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(1, binding.root)
                    }
                    "Fevereito" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(2, binding.root)
                    }
                    "Março" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(3, binding.root)
                    }
                    "Abril" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(4, binding.root)
                    }
                    "Maio" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(5, binding.root)
                    }
                    "Junho" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(6, binding.root)
                    }
                    "Julho" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(7, binding.root)
                    }
                    "Agosto" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(8, binding.root)
                    }
                    "Setembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(9, binding.root)
                    }
                    "Outubro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(10, binding.root)
                    }
                    "Novembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(11, binding.root)
                    }
                    "Dezembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaProdutoByMonth(12, binding.root)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    @SuppressLint("ResourceType")
    private fun bindForFornecedor() {
        binding.rvList.adapter = fornecedorAdapter
        swapAdapterWithManualSlide(binding.rvList.adapter as FornecedorListAdapter, binding.rvList)
        binding.searchMenuAgenda.visibility = View.GONE
        binding.searchMenu.visibility = View.VISIBLE
        val swipeParams = binding.swipeRefreshLayout.layoutParams as ConstraintLayout.LayoutParams
        swipeParams.topToBottom = binding.searchMenu.id
        if (contextStatus) {
            binding.spinnerStatus.setSelection(0)
        }
        binding.swipeRefreshLayout.layoutParams = swipeParams
        binding.iconAddsScreenContext.setOnClickListener {
            showRegisterFornecedorScreen()
        }
        binding.iconSearch.setOnClickListener {
            val textToSearch = binding.searchBar.text.toString()
            viewModel.searchFornecedores(binding.root, textToSearch)
        }
        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                contextStatus = false
                when (itemSelecionado) {
                    "Status" -> {}
                    "Active" -> {
                        contextStatus = true
                        viewModel.listAgendaProdByStatus(Situacao.ATIVA)
                    }
                    "Inactive" -> {
                        contextStatus = true
                        viewModel.listAgendaProdByStatus(Situacao.INATIVA)
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }

    @SuppressLint("ResourceType")
    private fun bindAgendaPayment() {
        binding.rvList.adapter = agendaPagamentoAdapter
        swapAdapterWithManualSlide(binding.rvList.adapter as AgendaPagamentoListAdapter, binding.rvList)
        binding.searchMenu.visibility = View.GONE
        binding.searchMenuAgenda.visibility = View.VISIBLE
        val resourceFilterStatus = resources.getStringArray(R.array.status_AgendaPag)
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resourceFilterStatus)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStatusAgenda.adapter = statusAdapter
        if (contextStatus) {
            binding.spinnerStatusAgenda.setSelection(0)
        }
        if (contextMonthFilter) {
            binding.monthFilter.setSelection(0)
        }
        val swipeParams = binding.swipeRefreshLayout.layoutParams as ConstraintLayout.LayoutParams
        swipeParams.topToBottom = binding.searchMenuAgenda.id
        binding.swipeRefreshLayout.layoutParams = swipeParams
        binding.iconAddsScreenContext.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AgendaPagamentoActivity::class.java))
        }
        binding.spinnerStatusAgenda.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                contextStatus = false
                when (itemSelecionado) {
                    "Status" -> {}
                    "Não pago" -> {
                        contextStatus = true
                        viewModel.listAgendaPagByStatus(SituacaoPagamento.A_PAGAR, binding.root)
                    }
                    "Pago" -> {
                        contextStatus = true
                        viewModel.listAgendaPagByStatus(SituacaoPagamento.PAGA, binding.root)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }


        }

        binding.monthFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()
                contextMonthFilter = false
                when (itemSelecionado) {
                    "Selecione a opção" -> {}
                    "Janeiro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(1, binding.root)
                    }
                    "Fevereito" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(2, binding.root)
                    }
                    "Março" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(3, binding.root)
                    }
                    "Abril" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(4, binding.root)
                    }
                    "Maio" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(5, binding.root)
                    }
                    "Junho" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(6, binding.root)
                    }
                    "Julho" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(7, binding.root)
                    }
                    "Agosto" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(8, binding.root)
                    }
                    "Setembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(9, binding.root)
                    }
                    "Outubro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(10, binding.root)
                    }
                    "Novembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(11, binding.root)
                    }
                    "Dezembro" -> {
                        contextMonthFilter = true
                        viewModel.listAgendaPagamento(12, binding.root)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun showRegisterAgendaProdutoScren() {
        startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
    }
    private fun showRegisterAgendaPagamentoScreen() {
        startActivity(Intent(this@HomeActivity, AgendaPagamentoActivity::class.java))
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
        if (contextScreen == StateStart.FORNECEDOR) {
            startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
        }
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showEmptyList() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi encontrado", Snackbar.LENGTH_SHORT).show()
    }
}

