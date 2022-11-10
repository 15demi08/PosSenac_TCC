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
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoCategoria
import com.demetriusjr.mystuff.fragmentos.dialogos.DialogoConfirmarExclusao
import com.demetriusjr.mystuff.fragmentos.utilidades.CategoriasAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarioDetalhesCategorias(private val viewModel:MyStuffViewModel):CategoriasAdapter.CACL, Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesLocaisBinding
    private val b get() = _b

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

            btnNovoItemLista.setOnClickListener {
                viewModel.categoriaSelecionada = null
                dialogoCategoria()
            }
            rclvLista.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarioDetalhesCategorias.requireContext())
                adapter = CategoriasAdapter(this@FragmentoInventarioDetalhesCategorias)
            }
            txtvNenhumItemLista.text = getText(R.string.inventariosDetalhesCategoriasNenhumMsg)

        }

        viewModel.categorias(viewModel.inventarioSelecionado!!.idInventario).observe(viewLifecycleOwner) { lista ->

            (if (lista.isEmpty()) View.VISIBLE else View.GONE).let { visibilidade ->
                b.setaLista.visibility = visibilidade
                b.txtvNenhumItemLista.visibility = visibilidade
            }
            (b.rclvLista.adapter as CategoriasAdapter).submitList(lista)

        }

    }

    override fun onClick(obj:Categoria) = mostrarToast()

    override fun onBtnClick(v:View, obj:Categoria) {
        viewModel.categoriaSelecionada = obj
        when ((v as ImageButton).id) {
            R.id.btnEditar -> dialogoCategoria()
            R.id.btnExcluir -> DialogoConfirmarExclusao(
                viewModel,
                R.string.dialogoExclusaoCategoria,
                DialogoConfirmarExclusao.CATEGORIA
            ).show(parentFragmentManager, "excluirCategoria")
        }
    }

    private fun dialogoCategoria() = DialogoCategoria(viewModel, layoutInflater).show(parentFragmentManager, "novaCategoria")

    private fun mostrarToast() = Toast.makeText(context, R.string.toastNaoImplementado, Toast.LENGTH_SHORT).show()

    companion object {
        @JvmStatic
        fun newInstance(vm:MyStuffViewModel) = FragmentoInventarioDetalhesCategorias(vm)
    }

}
