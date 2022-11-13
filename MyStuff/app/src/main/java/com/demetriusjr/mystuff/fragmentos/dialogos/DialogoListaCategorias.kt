package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoListaCategorias(
    val viewModel:MyStuffViewModel,
    private val dialogoPai:DialogoItem,
    private val listaCategorias:List<Categoria>,
    private var categoriasEscolhidas:ArrayList<Categoria>? = null
):DialogFragment() {

    private var selecionados:BooleanArray? = null

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog {

        val nomes = Array(listaCategorias.size){ listaCategorias[it].nome }

        if(categoriasEscolhidas == null) {

            categoriasEscolhidas = ArrayList()

        } else {

            val s = ArrayList<Boolean>()

            listaCategorias.forEach {
                if(categoriasEscolhidas!!.contains(it)) s.add(true)
                else s.add(false)
            }

            selecionados = BooleanArray(s.size){ s[it] }

        }

        val b = AlertDialog.Builder(requireActivity())
            .setIcon(R.drawable.ic_round_tag)
            .setTitle(R.string.dialogoListaCategoriaTitulo)
            .setMultiChoiceItems(nomes, selecionados) { _, i, selecionado ->
                if (selecionado)
                    categoriasEscolhidas?.add(listaCategorias[i])
                else
                    categoriasEscolhidas?.remove(listaCategorias[i])
            }
            .setPositiveButton(R.string.btnSalvar) { _, _ ->
                dialogoPai.categoriasSelecionadas.value = categoriasEscolhidas!!.toMutableList()
            }
            .setNeutralButton(R.string.btnCancelar, null)

        return b.create()

    }

}