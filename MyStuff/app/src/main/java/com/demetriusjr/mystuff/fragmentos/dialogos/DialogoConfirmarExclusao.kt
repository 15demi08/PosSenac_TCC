package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoConfirmarExclusao(val viewModel:MyStuffViewModel, val msg:Int, val codDialogo:Int):DialogFragment() {

    companion object {
        const val INVENTARIO = 1
        const val LOCAL = 2
        const val CATEGORIA = 3
        const val ITEM = 4
    }

    private lateinit var dialogo:AlertDialog.Builder

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        dialogo = AlertDialog.Builder(requireContext()).apply {

            setTitle(R.string.confirmarExclusao)
            setMessage(msg)
            setPositiveButton(R.string.menuExcluir, DialogInterface.OnClickListener { _, _ ->
                viewModel.apply {
                    when(codDialogo){
                        INVENTARIO -> excluir(inventarioSelecionado!!)
                        LOCAL -> excluir(localSelecionado!!)
                        CATEGORIA -> excluir(categoriaSelecionada!!)
                    }
                }
            })
            setNeutralButton(R.string.btnCancelar, null)

        }

    }

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog = dialogo.create()

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.apply {
            when(codDialogo){
                INVENTARIO -> inventarioSelecionado = null
                LOCAL -> localSelecionado = null
                CATEGORIA -> categoriaSelecionada = null
            }
        }
    }

}