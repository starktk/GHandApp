package com.example.ghandapp.agenda.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.agenda.data.local.AgendaModel
import com.example.ghandapp.databinding.AgendaItemBinding

class AgendaListAdapter: RecyclerView.Adapter<AgendaViewHolder>() {

    private val list: MutableList<AgendaModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaViewHolder {
        val binding = AgendaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AgendaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(items: List<AgendaModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }


}