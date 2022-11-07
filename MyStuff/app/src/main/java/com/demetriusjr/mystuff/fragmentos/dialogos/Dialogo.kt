package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

open class Dialogo(val viewModel:MyStuffViewModel):DialogFragment() {

    protected lateinit var dialogoBinding:DialogoBinding
    private lateinit var builder:AlertDialog.Builder

    protected var idTitulo:Int = 0

    protected var visibilidadeCamposAdicionais:Int = View.VISIBLE

    protected lateinit var positivoClickListener:OnClickListener

    protected var idNeutro:Int = 0
    protected var neutroClickListener:OnClickListener? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        dialogoBinding.CamposContainer.visibility = visibilidadeCamposAdicionais

        builder = AlertDialog.Builder(requireActivity())
            .setTitle(idTitulo)
            .setView(dialogoBinding.root)
            .setPositiveButton(R.string.btnSalvar, positivoClickListener)
            .setNeutralButton(idNeutro, neutroClickListener)

    }

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog = builder.create()

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.objetoSelecionadoOpcoes = null
    }

}