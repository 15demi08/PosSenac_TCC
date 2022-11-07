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

        if(viewModel.objetoSelecionadoOpcoes == null){

            idTitulo = R.string.inventariosDialogoNovoInvTitulo

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.inserir(Inventario(0, dialogoBinding.txteNome.text.toString()))
            }

        } else {

            idTitulo = R.string.inventariosDialogoEditarInvTitulo

            dialogoBinding.txteNome.setText((viewModel.objetoSelecionadoOpcoes as Inventario).nome)

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.apply {
                    (objetoSelecionadoOpcoes as Inventario).let {
                        atualizar(it.copy(nome = dialogoBinding.txteNome.text.toString()))
                    }
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

}