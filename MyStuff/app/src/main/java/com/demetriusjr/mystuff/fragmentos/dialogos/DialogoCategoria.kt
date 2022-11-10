package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoCategoria(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.categoriaSelecionada == null){

            idTitulo = R.string.inventarioDetalhesDialogoNovaCategoriaTitulo

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.inserir(Categoria(
                    0,
                    dialogoBinding.txteNome.text.toString(),
                    viewModel.inventarioSelecionado!!.idInventario
                ))
            }

        } else {

            idTitulo = R.string.inventarioDetalhesDialogoEditarCategoriaTitulo

            dialogoBinding.txteNome.setText((viewModel.categoriaSelecionada!!.nome))

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                viewModel.apply {
                    atualizar(categoriaSelecionada!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.categoriaSelecionada = null
    }

}