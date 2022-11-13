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
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoLocal
import com.demetriusjr.mystuff.fragmentos.utilidades.LocaisAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

open class FragmentoInventarioDetalhesLocais():LocaisAdapter.LACL, Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesLocaisBinding
    private val b get() = _b

    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao)

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesLocaisBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {

            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarioDetalhesLocais.requireContext())
                adapter = LocaisAdapter(this@FragmentoInventarioDetalhesLocais)
            }
            txtvNenhumItemLista.text = getText(R.string.msgLocalNenhum)

        }

        viewModel.locais(viewModel.inventarioSelecionado!!.idInventario).observe(viewLifecycleOwner) { lista ->

            (if (lista.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                b.txtvNenhumItemLista.visibility = visibilidade
            }
            (b.rclvLista.adapter as LocaisAdapter).submitList(lista)

        }

    }

    // Clique no item da lista
    override fun onClick(v:View, obj:Local) {
        // Por implementar
    }

    override fun onBtnClick(v:View, obj:Local) {
        viewModel.localSelecionado = obj
        when ((v as ImageButton).id) {
            R.id.btnEditar -> DialogoLocal(viewModel, layoutInflater).show(parentFragmentManager, "editarLocal")
            R.id.btnExcluir -> DialogoConfirmarExclusao(
                viewModel,
                R.string.msgExclusaoLocal,
                DialogoConfirmarExclusao.LOCAL
            ).show(parentFragmentManager, "excluirLocal")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarioDetalhesLocais()
    }

}