package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.databinding.CategoriaBinding
import com.demetriusjr.mystuff.databinding.ItemListaItemBinding
import com.demetriusjr.mystuff.db.Item
import com.demetriusjr.mystuff.db.ItemComLocalCategorias

class ItensAdapter(val cl:ItACL, val inflater:LayoutInflater):ListAdapter<ItemComLocalCategorias, ItensAdapter.ItAVH>(ItemComparator()) {

    // ItensAdapterViewHolder
    class ItAVH(layout:ItemListaItemBinding):RecyclerView.ViewHolder(layout.root)

    // ItensAdapterClickListener
    interface ItACL:BACL<ItemComLocalCategorias>

    class ItemComparator:DiffUtil.ItemCallback<ItemComLocalCategorias>() {
        override fun areItemsTheSame(oldItem:ItemComLocalCategorias, newItem:ItemComLocalCategorias):Boolean = oldItem.item.idItem == newItem.item.idItem
        override fun areContentsTheSame(oldItem:ItemComLocalCategorias, newItem:ItemComLocalCategorias):Boolean = oldItem.item.nome == newItem.item.nome
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ItAVH =
        ItAVH(ItemListaItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder:ItAVH, position:Int) {
        ItemListaItemBinding.bind(holder.itemView).apply {
            getItem(position).apply {

                txtvItemQuantidade.text = this.item.quantidade.toString()
                txtvItemNome.text = this.item.nome

                layoutLocal.txtvLocalNome.text = this.local.nome

                this.categorias.forEach {
                    val categoriaBinding = CategoriaBinding.inflate(inflater, null, false)
                    categoriaBinding.txtvCategoriaNome.text = it.nome
                    categoriaBinding.root.id = it.idCategoria.toInt()
                    categoriasContainer.addView(categoriaBinding.root)
                }

                itemContainer.setOnClickListener { cl.onClick(it, this) }
                btnItemEditar.setOnClickListener { cl.onBtnClick(it, this) }
                btnItemExcluir.setOnClickListener { cl.onBtnClick(it, this) }

            }
        }
    }

}