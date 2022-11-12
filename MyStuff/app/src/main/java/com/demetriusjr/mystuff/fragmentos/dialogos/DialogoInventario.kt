package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoInventario(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.inventarioSelecionado == null){

            idTitulo = R.string.dialogoInventarioNovoTitulo

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.inserir(Inventario(0, dialogoBinding.txteNome.text.toString()))
            }

        } else {

            idTitulo = R.string.dialogoInventarioEditarTitulo

            dialogoBinding.txteNome.setText(viewModel.inventarioSelecionado!!.nome)

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.apply {
                    atualizar(inventarioSelecionado!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.inventarioSelecionado = null
    }

}