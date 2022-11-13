package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.databinding.CategoriaBinding
import com.demetriusjr.mystuff.databinding.ItemListaItemBinding
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.db.ItemComLocalCategorias

class ItensAdapter(val cl:ItACL, val inflater:LayoutInflater):ListAdapter<ItemComLocalCategorias, ItensAdapter.ItAVH>(ItemComparator()) {

    // ItensAdapterViewHolder
    class ItAVH(layout:ItemListaItemBinding):RecyclerView.ViewHolder(layout.root)

    // ItensAdapterClickListener
    interface ItACL:BACL<ItemComLocalCategorias>

    class ItemComparator:DiffUtil.ItemCallback<ItemComLocalCategorias>() {
        override fun areItemsTheSame(oldItem:ItemComLocalCategorias, newItem:ItemComLocalCategorias):Boolean = oldItem.item.idItem == newItem.item.idItem
        override fun areContentsTheSame(oldItem:ItemComLocalCategorias, newItem:ItemComLocalCategorias):Boolean {
            return oldItem.item.nome == newItem.item.nome &&
                    oldItem.local.nome == newItem.local.nome &&
                    iguais(oldItem.categorias, newItem.categorias)
        }
        private fun iguais(lista1:List<Categoria>, lista2:List<Categoria>):Boolean{
            if (lista1.size != lista2.size) return false
            else if (lista1 != lista2) return false
            else {
                lista1.forEachIndexed { index, categoria ->
                    if (categoria != lista2[index]) return false
                }
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ItAVH =
        ItAVH(ItemListaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder:ItAVH, position:Int) {

        ItemListaItemBinding.bind(holder.itemView).apply {

            val flowIDs = ArrayList<Int>()

            // root.invalidate()
            //
            // flowIDs.forEach {
            //     root.removeView(root.findViewById(it))
            // }
            //
            // flowIDs.removeAll(flowIDs.toSet())
            // flowCategoriasContainer.referencedIds = IntArray(0)

            getItem(position).let { itemComLocalCategorias ->

                txtvItemQuantidade.text = itemComLocalCategorias.item.quantidade.toString()
                txtvItemNome.text = itemComLocalCategorias.item.nome

                layoutLocal.txtvLocalNome.text = itemComLocalCategorias.local.nome

                itemComLocalCategorias.categorias.forEach {

                    CategoriaBinding.inflate(inflater, itemContainer, false).apply {
                        txtvCategoriaNome.text = it.nome
                        this.root.id = it.idCategoria.toInt()
                        flowIDs.add(this.root.id)
                        itemContainer.addView(this.root)
                    }

                    flowCategoriasContainer.referencedIds = IntArray(flowIDs.size) { flowIDs[it] }

                }

                btnItemEditar.setOnClickListener { cl.onBtnClick(it, itemComLocalCategorias) }
                btnItemExcluir.setOnClickListener { cl.onBtnClick(it, itemComLocalCategorias) }

            }
        }
    }

}