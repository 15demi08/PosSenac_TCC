package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoCategoria(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        idIcone = R.drawable.ic_round_tag

        visibilidadeCamposAdicionais = View.GONE

        if(viewModel.categoriaSelecionada == null){

            idTitulo = R.string.dialogoCategoriaNovaTitulo

            configurarCampoNome(" ")

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                if (validoNome.value!!) {
                    viewModel.inserir(Categoria(
                        0,
                        dialogoBinding.txteNome.text.toString(),
                        viewModel.inventarioSelecionado!!.idInventario
                    ))
                } else {
                    mostrarSnackBar(R.string.msgNaoSalvoCategoria)
                }
            }

        } else {

            idTitulo = R.string.dialogoCategoriaEditarTitulo

            dialogoBinding.txteNome.setText((viewModel.categoriaSelecionada!!.nome))

            positivoClickListener = DialogInterface.OnClickListener { _,_ ->
                if (validoNome.value!!) {
                    viewModel.apply {
                        atualizar(categoriaSelecionada!!.copy(nome = dialogoBinding.txteNome.text.toString()))
                    }
                } else {
                    mostrarSnackBar(R.string.msgNaoAtualizadoCategoria)
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.categoriaSelecionada != null){
            configurarCampoNome(viewModel.categoriaSelecionada!!.nome)
        }
    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.categoriaSelecionada = null
    }

}