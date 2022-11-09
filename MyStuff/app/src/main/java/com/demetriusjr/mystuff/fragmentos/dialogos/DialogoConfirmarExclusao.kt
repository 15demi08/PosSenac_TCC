package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoConfirmarExclusao(val viewModel:MyStuffViewModel, val msg:Int):DialogFragment() {

    private lateinit var dialogo:AlertDialog.Builder

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        dialogo = AlertDialog.Builder(requireContext()).apply {

            setTitle(R.string.confirmarExclusao)
            setMessage(msg)
            setPositiveButton(R.string.menuExcluir, DialogInterface.OnClickListener { _, _ ->
                viewModel.apply {
                    objetoSelecionadoOpcoes.let {
                        when (it) {
                            is Inventario -> excluir(it as Inventario)
                            is Local -> excluir(it as Local)
                        }
                    }
                }
            })
            setNeutralButton(R.string.btnCancelar, null)

        }

    }

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog = dialogo.create()

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.objetoSelecionadoOpcoes = null
    }

}