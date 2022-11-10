package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.databinding.ItemListaBinding
import com.demetriusjr.mystuff.db.Inventario

class InventariosAdapter(val cl:IACL):ListAdapter<Inventario, InventariosAdapter.IAVH>(InventariosComparator()) {

    // InventariosAdapterViewHolder
    class IAVH(layout:ItemListaBinding):RecyclerView.ViewHolder(layout.root)

    interface IACL:BACL<Inventario>

    class InventariosComparator:DiffUtil.ItemCallback<Inventario>() {
        override fun areItemsTheSame(oldItem:Inventario, newItem:Inventario):Boolean = oldItem.idInventario == newItem.idInventario // IDs iguais = teoricamente, itens iguais
        override fun areContentsTheSame(oldItem:Inventario, newItem:Inventario):Boolean = oldItem.nome == newItem.nome // IDs iguais && conteúdo igual = definitivamente itens iguais
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):IAVH =
        IAVH(ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder:IAVH, position:Int) {
        ItemListaBinding.bind(holder.itemView).apply {
            getItem(position).apply {
                txtvUmaLinhaNome.text = this.nome
                itemContainer.setOnClickListener { cl.onClick(this) }
                btnEditar.setOnClickListener { cl.onBtnClick(it, this) }
                btnExcluir.setOnClickListener { cl.onBtnClick(it, this) }
            }
        }
    }

}