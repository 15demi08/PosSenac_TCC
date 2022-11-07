package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesListaBinding
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoLocal
import com.demetriusjr.mystuff.fragmentos.utilidades.LocaisAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

open class FragmentoInventarioDetalhesLocais(val viewModel:MyStuffViewModel):LocaisAdapter.LACL, PopupMenu.OnMenuItemClickListener,  Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesListaBinding
    private val b get() = _b

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesListaBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {

            btnNovoItemLista.setOnClickListener { dialogoLocal() }
            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarioDetalhesLocais.requireContext())
                adapter = LocaisAdapter(this@FragmentoInventarioDetalhesLocais)
            }

        }

        viewModel.locais(viewModel.inventarioSelecionado!!.idInventario).observe(viewLifecycleOwner, Observer { lista ->

            (if (lista.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                b.setaLista.visibility = visibilidade
                b.txtvNenhumItemLista.visibility = visibilidade
            }
            (b.rclvLista.adapter as LocaisAdapter).submitList(lista)

        })

    }

    override fun onClick(local:Local) = mostrarToast()

    override fun onMenuClick(v:View, local:Local) {
        viewModel.objetoSelecionadoOpcoes = local
        PopupMenu(requireContext(), v).apply {
            setOnMenuItemClickListener(this@FragmentoInventarioDetalhesLocais)
            inflate(R.menu.item_opcoes)
            show()
        }
    }

    override fun onMenuItemClick(item:MenuItem):Boolean {
        when (item.itemId) {
            R.id.menuOpcaoEditar -> dialogoLocal()
            R.id.menuOpcaoExcluir -> DialogoConfirmarExclusao(viewModel).show(parentFragmentManager, "confirmarExlusao")
        }
        return false
    }

    fun dialogoLocal() = DialogoLocal(viewModel, layoutInflater).show(parentFragmentManager, "novoLocal")

    fun mostrarToast() = Toast.makeText(context, R.string.toastNaoImplementado, Toast.LENGTH_SHORT).show()

    companion object {
        @JvmStatic
        fun newInstance(vm:MyStuffViewModel) = FragmentoInventarioDetalhesLocais(vm)
    }

}