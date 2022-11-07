package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoLocal(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.objetoSelecionadoOpcoes == null){

            idTitulo = R.string.inventarioDetalhesDialogoNovoLocalTitulo

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.inserir(Local(
                    0,
                    dialogoBinding.txteNome.text.toString(),
                    viewModel.inventarioSelecionado!!.idInventario
                ))
            }

        } else {

            idTitulo = R.string.inventarioDetalhesDialogoEditarLocalTitulo

            dialogoBinding.txteNome.setText((viewModel.objetoSelecionadoOpcoes as Local).nome)

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.apply {
                    (objetoSelecionadoOpcoes as Local).let {
                        atualizar(it.copy(nome = dialogoBinding.txteNome.text.toString()))
                    }
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

}