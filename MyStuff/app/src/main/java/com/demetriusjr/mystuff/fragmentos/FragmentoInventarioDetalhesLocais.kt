package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesLocaisBinding
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoLocal
import com.demetriusjr.mystuff.fragmentos.utilidades.LocaisAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

open class FragmentoInventarioDetalhesLocais(private val viewModel:MyStuffViewModel):LocaisAdapter.LACL, Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesLocaisBinding
    private val b get() = _b

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

            btnNovoItemLista.setOnClickListener {
                viewModel.localSelecionado = null
                dialogoLocal()
            }
            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarioDetalhesLocais.requireContext())
                adapter = LocaisAdapter(this@FragmentoInventarioDetalhesLocais)
            }
            txtvNenhumItemLista.text = getText(R.string.inventariosDetalhesLocaisNenhumMsg)

        }

        viewModel.locais(viewModel.inventarioSelecionado!!.idInventario).observe(viewLifecycleOwner) { lista ->

            (if (lista.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                b.setaLista.visibility = visibilidade
                b.txtvNenhumItemLista.visibility = visibilidade
            }
            (b.rclvLista.adapter as LocaisAdapter).submitList(lista)

        }

    }

    override fun onClick(obj:Local) = mostrarToast()

    override fun onBtnClick(v:View, obj:Local) {
        viewModel.localSelecionado = obj
        when ((v as ImageButton).id) {
            R.id.btnEditar -> dialogoLocal()
            R.id.btnExcluir -> DialogoConfirmarExclusao(
                viewModel,
                R.string.dialogoExclusaoLocal,
                DialogoConfirmarExclusao.LOCAL
            ).show(parentFragmentManager, "excluirLocal")
        }
    }

    private fun dialogoLocal() = DialogoLocal(viewModel, layoutInflater).show(parentFragmentManager, "novoLocal")

    private fun mostrarToast() = Toast.makeText(context, R.string.toastNaoImplementado, Toast.LENGTH_SHORT).show()

    companion object {
        @JvmStatic
        fun newInstance(vm:MyStuffViewModel) = FragmentoInventarioDetalhesLocais(vm)
    }

}