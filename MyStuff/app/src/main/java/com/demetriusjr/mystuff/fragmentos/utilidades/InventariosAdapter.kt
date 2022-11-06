package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.databinding.ItemListaBinding
import com.demetriusjr.mystuff.db.Inventario

class InventariosAdapter(val cl:IACL):ListAdapter<Inventario, InventariosAdapter.ViewHolder>(InventariosComparator()) {

    class ViewHolder(layout:ItemListaBinding):RecyclerView.ViewHolder(layout.root)

    class InventariosComparator:DiffUtil.ItemCallback<Inventario>() {
        override fun areItemsTheSame(oldItem:Inventario, newItem:Inventario):Boolean = oldItem.idInventario == newItem.idInventario // IDs iguais = itens iguais, teoricamente
        override fun areContentsTheSame(oldItem:Inventario, newItem:Inventario):Boolean = oldItem.nome == newItem.nome // IDs iguais && conteúdo igual = definitivamente itens iguais
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder =
        ViewHolder(ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder:ViewHolder, position:Int) {
        ItemListaBinding.bind(holder.itemView).apply {
            getItem(position).apply {
                txtvEtiquetaPrimaria.text = this.nome
                itemContainer.setOnClickListener {
                    cl.onClick(this)
                }
            }
        }
    }

    interface IACL { // InventariosAdapterClickListener
        fun onClick(i:Inventario)
    }

}
