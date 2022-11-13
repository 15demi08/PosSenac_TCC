package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesLocaisBinding
import com.demetriusjr.mystuff.db.ItemComLocalCategorias
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoItem
import com.demetriusjr.mystuff.fragmentos.utilidades.ItensAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarioDetalhesItens():ItensAdapter.ItACL, Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesLocaisBinding
    private val b get() = _b

    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao)

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesLocaisBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {

            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarioDetalhesItens.requireContext())
                adapter = ItensAdapter(this@FragmentoInventarioDetalhesItens, layoutInflater)
            }

        }

        val idInventario = viewModel.inventarioSelecionado!!.idInventario
        val locaisLiveData = viewModel.quantidadeLocais(idInventario)
        val categoriasLiveData = viewModel.quantidadeCategorias(idInventario)
        val itensLiveData = viewModel.itens(idInventario)

        locaisLiveData.observe(viewLifecycleOwner) { qtdeLocais ->

            categoriasLiveData.observe(viewLifecycleOwner) { qtdeCategorias ->

                if (qtdeLocais == 0 || qtdeCategorias == 0) {

                    viewModel.podeIncluirItens = false

                    b.txtvNenhumItemLista.text = getText(R.string.msgItemNenhumOutro)
                    b.txtvNenhumItemLista.visibility = View.VISIBLE

                    if (itensLiveData.hasObservers())
                        itensLiveData.removeObservers(viewLifecycleOwner)


                } else {

                    viewModel.podeIncluirItens = true

                    itensLiveData.observe(viewLifecycleOwner) { listaItens ->

                        (if (listaItens.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                            b.txtvNenhumItemLista.text = getText(R.string.msgItemNenhum)
                            b.txtvNenhumItemLista.visibility = visibilidade
                        }
                        (b.rclvLista.adapter as ItensAdapter).submitList(listaItens)
                    }
                }

            }
        }

    }

    override fun onClick(v:View, obj:ItemComLocalCategorias) {
        // Por implementar
    }

    override fun onBtnClick(v:View, obj:ItemComLocalCategorias) {
        viewModel.itemSelecionado = obj
        when ((v as ImageButton).id) {
            R.id.btnItemEditar -> DialogoItem(viewModel, layoutInflater).show(parentFragmentManager, "editarItem")
            R.id.btnItemExcluir -> DialogoConfirmarExclusao(
                viewModel,
                R.string.msgExclusaoItem,
                DialogoConfirmarExclusao.ITEM
            ).show(parentFragmentManager, "excluirItem")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarioDetalhesItens()
    }

}
