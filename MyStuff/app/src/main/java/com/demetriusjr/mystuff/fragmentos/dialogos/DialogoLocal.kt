package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoLocal(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        idIcone = R.drawable.ic_round_local

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.localSelecionado == null){

            idTitulo = R.string.dialogoLocalNovoTitulo

            configurarCampoNome(" ")

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                if (validoNome.value!!) {
                    viewModel.inserir(Local(
                        0,
                        dialogoBinding.txteNome.text.toString(),
                        viewModel.inventarioSelecionado!!.idInventario
                    ))
                } else {
                    mostrarSnackBar(R.string.msgNaoSalvoLocal)
                }
            }

        } else {

            idTitulo = R.string.dialogoLocalEditarTitulo

            dialogoBinding.txteNome.setText(viewModel.localSelecionado!!.nome)

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                if (validoNome.value!!) {
                    viewModel.apply {
                        atualizar(localSelecionado!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                    }
                } else {
                    mostrarSnackBar(R.string.msgNaoAtualizadoLocal)
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.localSelecionado != null){
            configurarCampoNome(viewModel.localSelecionado!!.nome)
        }
    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.localSelecionado = null
    }

}