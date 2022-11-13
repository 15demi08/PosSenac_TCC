package com.demetriusjr.mystuff.fragmentos.utilidades

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demetriusjr.mystuff.fragmentos.FragmentoInventarioDetalhesCategorias
import com.demetriusjr.mystuff.fragmentos.FragmentoInventarioDetalhesItens
import com.demetriusjr.mystuff.fragmentos.FragmentoInventarioDetalhesLocais
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class InventarioDetalhesPaginasAdapter(f:Fragment, private val vm:MyStuffViewModel):FragmentStateAdapter(f) {

    override fun getItemCount():Int = 3

    override fun createFragment(position:Int):Fragment {
        return when (position) {
            0 -> FragmentoInventarioDetalhesLocais()
            1 -> FragmentoInventarioDetalhesCategorias()
            else -> FragmentoInventarioDetalhesItens() // Gambiarra
        }
    }

}