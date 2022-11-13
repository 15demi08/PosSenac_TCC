package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.ItemListaBinding
import com.demetriusjr.mystuff.db.Categoria

class CategoriasAdapter(val cl:CACL):ListAdapter<Categoria, CategoriasAdapter.CAVH>(ItemComparator()) {

    // CategoriasAdapterViewHolder
    class CAVH(layout:ItemListaBinding):RecyclerView.ViewHolder(layout.root)

    // CategoriasAdapterClickListener
    interface CACL:BACL<Categoria>

    class ItemComparator:DiffUtil.ItemCallback<Categoria>() {
        override fun areItemsTheSame(oldItem:Categoria, newItem:Categoria):Boolean = oldItem.idCategoria == newItem.idCategoria
        override fun areContentsTheSame(oldItem:Categoria, newItem:Categoria):Boolean = oldItem.nome == newItem.nome
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):CAVH =
        CAVH(ItemListaBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder:CAVH, position:Int) {
        ItemListaBinding.bind(holder.itemView).apply {
            getItem(position).let { categoria ->
                itemListaIcone.setImageResource(R.drawable.ic_round_tag)
                txtvUmaLinhaNome.text = categoria.nome
                btnEditar.setOnClickListener { cl.onBtnClick(it, categoria) }
                btnExcluir.setOnClickListener { cl.onBtnClick(it, categoria) }
            }
        }
    }

}