package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoListaLocais(val viewModel:MyStuffViewModel, private val dialogoPai:DialogoItem, private val listaLocais:List<Local>):DialogFragment() {

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog {

        val b = AlertDialog.Builder(requireActivity())

        b.setIcon(R.drawable.ic_round_local)
        b.setTitle(R.string.dialogoListaLocaisTitulo)

        val nomes = Array(listaLocais.size){ listaLocais[it].nome!! }

        b.setItems(nomes) { _, i ->
            dialogoPai.localSelecionado.value = listaLocais[i]
        }

        return b.create()

    }

}