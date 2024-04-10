package com.example.ghandapp.agenda.agendaPagamento.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.databinding.AgendapagamentoItemBinding

class AgendaPagamentoListAdapter: RecyclerView.Adapter<AgendaPagamentoViewHolder>() {

    private val list: MutableList<AgendaPagamentoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaPagamentoViewHolder {
        val binding = AgendapagamentoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaPagamentoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AgendaPagamentoViewHolder, position: Int) {
        holder.bind(list[position])
    }
    fun add(items: List<AgendaPagamentoModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

}