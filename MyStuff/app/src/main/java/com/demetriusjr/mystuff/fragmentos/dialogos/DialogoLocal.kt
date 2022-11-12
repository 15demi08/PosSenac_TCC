package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoLocal(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.localSelecionado == null){

            idTitulo = R.string.dialogoLocalNovoTitulo

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.inserir(Local(
                    0,
                    dialogoBinding.txteNome.text.toString(),
                    viewModel.inventarioSelecionado!!.idInventario
                ))
            }

        } else {

            idTitulo = R.string.dialogoLocalEditarTitulo

            dialogoBinding.txteNome.setText(viewModel.localSelecionado!!.nome)

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.apply {
                    atualizar(localSelecionado!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.localSelecionado = null
    }

}