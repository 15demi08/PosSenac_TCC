package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.view.LayoutInflater
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.db.Item
import com.demetriusjr.mystuff.db.ItemComCategorias
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoItem(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    private var localSelecionado:Local? = null
    private val categoriasSelecionadas = ArrayList<Categoria>()

    // *******************************************************
    // >> TESTAR DIÁLOGOS SOBREPOSTOS P/ LOCAL E CATEGORIAS <<
    // *******************************************************
    //

    init {

        dialogoBinding = DialogoBinding.inflate(inflater)

        if (viewModel.itemSelecionado == null) {

            idTitulo = R.string.dialogoitemNovoTitulo

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
                viewModel.inserir(
                    ItemComCategorias(
                        Item(
                            0,
                            dialogoBinding.txteNome.text.toString(),
                            dialogoBinding.txteQuantidade.toString().toLong(),
                            viewModel.inventarioSelecionado!!.idInventario,
                            localSelecionado!!.idLocal
                        ),
                        categoriasSelecionadas
                    )
                )
            }

        } else {

            idTitulo = R.string.dialogoItemEditarTitulo

            dialogoBinding.txteNome.setText(viewModel.itemSelecionado!!.item.nome)
            dialogoBinding.txteQuantidade.setText(viewModel.itemSelecionado!!.item.quantidade.toString())

            // Configurar os dois abaixo
            // dialogoBinding.actvLocal
            // dialogoBinding.actvCategoria

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
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