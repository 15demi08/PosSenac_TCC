package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoInventario(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        idIcone = R.drawable.ic_round_inventario

        visibilidadeCamposAdicionais = View.GONE

        if (viewModel.inventarioSelecionado == null) {

            idTitulo = R.string.dialogoInventarioNovoTitulo

            configurarCampoNome(" ")

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
                if (validoNome.value!!) {
                    viewModel.inserir(Inventario(0, dialogoBinding.txteNome.text.toString()))
                } else {
                    mostrarSnackBar(R.string.msgNaoSalvoInventario)
                }
            }

        } else {

            idTitulo = R.string.dialogoInventarioEditarTitulo

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
                if (validoNome.value!!) {
                    viewModel.apply {
                        atualizar(inventarioSelecionado!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                    }
                } else {
                    mostrarSnackBar(R.string.msgNaoAtualizadoInventario)
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.inventarioSelecionado != null){
            configurarCampoNome(viewModel.inventarioSelecionado!!.nome)
        }
    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.inventarioSelecionado = null
    }

}