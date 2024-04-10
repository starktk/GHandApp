package com.example.ghandapp.agenda.agendaPagamento.view

import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.databinding.AgendapagamentoItemBinding

class AgendaPagamentoViewHolder(private val binding: AgendapagamentoItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(agenda: AgendaPagamentoModel) {
        binding.valueToPayCd.text = agenda.valueToPay.toString()
        binding.dateAgendaCd.text = agenda.dateToPayOrReceive
        binding.statusCd.text = agenda.status
    }
}