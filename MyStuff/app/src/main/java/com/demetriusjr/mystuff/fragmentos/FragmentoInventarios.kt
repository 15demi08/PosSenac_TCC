package com.demetriusjr.mystuff.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demetriusjr.mystuff.MyStuffApplication
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventariosBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoInventario
import com.demetriusjr.mystuff.fragmentos.utilidades.InventariosAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel
import com.demetriusjr.mystuff.viewModels.MyStuffViewModelFactory

class FragmentoInventarios:InventariosAdapter.IACL, PopupMenu.OnMenuItemClickListener,  Fragment() {

    private lateinit var _b:FragmentoInventariosBinding
    private val b get() = _b



    private lateinit var app:MyStuffApplication
    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao) { MyStuffViewModelFactory(app.repositorio) }

    override fun onAttach(context:Context) {
        super.onAttach(context)
        app = activity?.application as MyStuffApplication
    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventariosBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {
            novoInventario.setOnClickListener { dialogoInventario() }
            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarios.requireContext())
                adapter = InventariosAdapter(this@FragmentoInventarios)
            }
        }

        viewModel.inventarios.observe(viewLifecycleOwner, Observer { lista ->

            (if (lista.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                b.setaInventarios.visibility = visibilidade
                b.txtvNenhumInventario.visibility = visibilidade
            }
            (b.rclvLista.adapter as InventariosAdapter).submitList(lista)

        })
    }

    fun dialogoInventario() = DialogoInventario(viewModel, layoutInflater).show(parentFragmentManager, "dialogoInventario")

    override fun onClick(inventario:Inventario) {
        viewModel.inventarioSelecionado = inventario
        findNavController().navigate(R.id.action_fragmentoInventarios_to_fragmentoInventarioDetalhes)
    }

    // Adapter
    override fun onMenuClick(v:View, inventario:Inventario) {
        viewModel.objetoSelecionadoOpcoes = inventario
        PopupMenu(requireContext(), v).apply {
            setOnMenuItemClickListener(this@FragmentoInventarios)
            inflate(R.menu.item_opcoes)
            show()
        }
    }

    // PopupMenu
    override fun onMenuItemClick(item:MenuItem):Boolean {
        when (item.itemId) {
            R.id.menuOpcaoEditar -> dialogoInventario()
            R.id.menuOpcaoExcluir -> DialogoConfirmarExclusao(viewModel).show(parentFragmentManager, "confirmarExlusao")
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarios()
    }


}
