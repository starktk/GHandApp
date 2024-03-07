package com.example.ghandapp.agenda.view

import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.databinding.AgendaItemBinding

class AgendaViewHolder(private val binding: AgendaItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(agenda: AgendaModel) {
        binding.nameProductCd.text = agenda.nomeProduct
        binding.amountCd.text = agenda.amount.toString()
        binding.dateAgendaCd.text = agenda.date
        binding.statusCd.text = agenda.status
    }
}