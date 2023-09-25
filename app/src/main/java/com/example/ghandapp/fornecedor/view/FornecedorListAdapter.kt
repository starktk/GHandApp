package com.example.ghandapp.fornecedor.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel

class FornecedorListAdapter: RecyclerView.Adapter<FornecedorViewHolder>() {

    private val list: MutableList<FornecedorModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FornecedorViewHolder {
        val binding = FornecedorListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FornecedorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FornecedorViewHolder, position: Int) {
        holder.bind(list[position])
    }
    fun add(items: List<FornecedorModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}