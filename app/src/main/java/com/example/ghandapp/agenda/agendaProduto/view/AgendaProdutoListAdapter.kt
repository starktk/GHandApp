package com.example.ghandapp.agenda.agendaProduto.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.databinding.AgendaprodutoItemBinding

class AgendaProdutoListAdapter: RecyclerView.Adapter<AgendaProdutoViewHolder>() {

    private val list: MutableList<AgendaProdutoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaProdutoViewHolder {
        val binding = AgendaprodutoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaProdutoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AgendaProdutoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(items: List<AgendaProdutoModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }


}