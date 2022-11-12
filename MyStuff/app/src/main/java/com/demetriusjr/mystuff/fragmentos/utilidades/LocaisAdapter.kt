package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.ItemListaBinding
import com.demetriusjr.mystuff.db.Local

class LocaisAdapter(val cl:LACL):ListAdapter<Local, LocaisAdapter.LAVH>(ItemComparator()) {

    // LocaisAdapterViewHolder
    class LAVH(layout:ItemListaBinding):RecyclerView.ViewHolder(layout.root)

    // LocaisAdapterClickListener
    interface LACL:BACL<Local>

    class ItemComparator:DiffUtil.ItemCallback<Local>() {
        override fun areItemsTheSame(oldItem:Local, newItem:Local):Boolean = oldItem.idLocal == newItem.idLocal
        override fun areContentsTheSame(oldItem:Local, newItem:Local):Boolean = oldItem.nome == newItem.nome
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):LAVH =
        LAVH(ItemListaBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder:LAVH, position:Int) {
        ItemListaBinding.bind(holder.itemView).apply {
            getItem(position).let { local ->
                itemListaIcone.setImageResource(R.drawable.ic_round_local)
                txtvUmaLinhaNome.text = local.nome
                btnEditar.setOnClickListener { cl.onBtnClick(it, local) }
                btnExcluir.setOnClickListener { cl.onBtnClick(it, local) }
            }
        }
    }

}