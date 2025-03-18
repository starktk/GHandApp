package com.example.ghandapp.agenda.agendaProduto.view

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.databinding.AgendaprodutoListItemBinding
import com.example.ghandapp.fornecedor.data.model.FornecedorModel

class AgendaProdutoListAdapter(private val onStatusChange: (AgendaProdutoModel) -> Unit): RecyclerView.Adapter<AgendaProdutoViewHolder>() {

    private val list: MutableList<AgendaProdutoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaProdutoViewHolder {
        val binding = AgendaprodutoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaProdutoViewHolder(binding, onStatusChange)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AgendaProdutoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(items: List<AgendaProdutoModel>) {
        list.clear()
        list.addAll(items)
        println(list)
        notifyDataSetChanged()
    }

    fun getObjectInListByPosition(position: Int): AgendaProdutoModel {
        return list[position]
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}