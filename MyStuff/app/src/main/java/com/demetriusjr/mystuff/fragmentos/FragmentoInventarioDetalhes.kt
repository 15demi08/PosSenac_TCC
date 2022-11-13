package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.demetriusjr.mystuff.MainActivity
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesBinding
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoCategoria
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoItem
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoLocal
import com.demetriusjr.mystuff.fragmentos.utilidades.InventarioDetalhesPaginasAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FragmentoInventarioDetalhes:Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesBinding
    private val b get() = _b

    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao)
    private lateinit var paginasAdapter:InventarioDetalhesPaginasAdapter

    private val pcc = object:OnPageChangeCallback() {
        override fun onPageScrolled(position:Int, positionOffset:Float, positionOffsetPixels:Int) {
            if (positionOffset != 0.0f) b.fabNovo.hide()
            else b.fabNovo.show()
        }
    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paginasAdapter = InventarioDetalhesPaginasAdapter(this, viewModel)

        val tabsEtiquetas = arrayOf(getText(R.string.inventarioDetalhesTabLocais), getText(R.string.inventarioDetalhesTabCategorias), getText(R.string.inventarioDetalhesTabItens))
        val tabsIcones = arrayOf(R.drawable.ic_round_local, R.drawable.ic_round_tag, R.drawable.ic_round_item)

        b.apply {

            txtvTitulo.text = viewModel.inventarioSelecionado!!.nome
            paginasInventarioDetalhes.adapter = paginasAdapter
            TabLayoutMediator(tabsInventarioDetalhes, paginasInventarioDetalhes) { tab, position ->
                tab.setIcon(tabsIcones[position])
                tab.text = tabsEtiquetas[position]
            }.attach()
            paginasInventarioDetalhes.registerOnPageChangeCallback(pcc)
            fabNovo.setOnClickListener {
                when (paginasInventarioDetalhes.currentItem) {
                    0 -> { // Locais
                        viewModel.localSelecionado = null
                        DialogoLocal(viewModel, layoutInflater).show(parentFragmentManager, "novoLocal")
                    }
                    1 -> { // Categorias
                        viewModel.categoriaSelecionada = null
                        DialogoCategoria(viewModel, layoutInflater).show(parentFragmentManager, "novaCategoria")
                    }
                    2 -> { // Itens
                        if (viewModel.podeIncluirItens) {
                            viewModel.itemSelecionado = null
                            DialogoItem(viewModel, layoutInflater).show(parentFragmentManager, "novoItem")
                        } else {
                            (requireActivity() as MainActivity).mostrarSnackbar(view, R.string.msgItemNenhumOutroSnack)
                        }
                    }
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarioDetalhes()
    }

}