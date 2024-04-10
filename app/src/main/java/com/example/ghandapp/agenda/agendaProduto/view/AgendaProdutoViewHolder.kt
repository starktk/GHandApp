package com.example.ghandapp.agenda.agendaProduto.view

import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.databinding.AgendaprodutoItemBinding

class AgendaProdutoViewHolder(private val binding: AgendaprodutoItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(agenda: AgendaProdutoModel) {
        binding.nameProductCd.text = agenda.nameProduct
        binding.amountCd.text = agenda.amount.toString()
        binding.dateAgendaCd.text = agenda.date
        binding.statusCd.text = agenda.status
    }
}