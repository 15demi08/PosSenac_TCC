package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.demetriusjr.mystuff.MyStuffApplication
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesBinding
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesLocaisBinding
import com.demetriusjr.mystuff.fragmentos.utilidades.InventarioDetalhesPaginasAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel
import com.demetriusjr.mystuff.viewModels.MyStuffViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class FragmentoInventarioDetalhes:Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesBinding
    private val b get() = _b

    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao)
    private lateinit var paginasAdapter:InventarioDetalhesPaginasAdapter

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paginasAdapter = InventarioDetalhesPaginasAdapter(this, viewModel )

        val tabsEtiquetas = arrayOf(
            getText(R.string.inventarioDetalhesTabLocais),
            getText(R.string.inventarioDetalhesTabCategorias),
            getText(R.string.inventarioDetalhesTabItens)
        )

        b.apply {

            txtvTitulo.text = viewModel.inventarioSelecionado!!.nome
            paginasInventarioDetalhes.adapter = paginasAdapter
            TabLayoutMediator(tabsInventarioDetalhes, paginasInventarioDetalhes){ tab, position ->
                tab.text = tabsEtiquetas[position]
            }.attach()

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarioDetalhes()
    }

}