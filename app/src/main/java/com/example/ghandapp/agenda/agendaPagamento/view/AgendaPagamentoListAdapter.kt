package com.example.ghandapp.agenda.agendaPagamento.view

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.databinding.AgendapagamentoListItemBinding

class AgendaPagamentoListAdapter(private val onStatusChange: (AgendaPagamentoModel) -> Unit): RecyclerView.Adapter<AgendaPagamentoViewHolder>() {

    private val list: MutableList<AgendaPagamentoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaPagamentoViewHolder {
        val binding = AgendapagamentoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaPagamentoViewHolder(binding, onStatusChange)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AgendaPagamentoViewHolder, position: Int) {
        holder.bind(list[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun add(items: List<AgendaPagamentoModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun getObjectInListByPosition(position: Int): AgendaPagamentoModel {
        return list[position]
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

}