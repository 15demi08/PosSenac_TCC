package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoNovoInventario(val viewModel:MyStuffViewModel):DialogFragment() {

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * [.onCreate] and [.onViewCreated].
     *
     * A default View can be returned by calling [.Fragment] in your
     * constructor. Otherwise, this method returns null.
     *
     *
     * It is recommended to **only** inflate the layout in this method and move
     * logic that operates on the returned View to [.onViewCreated].
     *
     *
     * If you return a View from here, you will later be called in
     * [.onDestroyView] when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog {

        return activity?.let {

            val v = DialogoBinding.inflate(it.layoutInflater).also {
                it.CamposContainer.visibility = View.GONE
            }

            AlertDialog.Builder(it)
                .setView(v.root)
                .setPositiveButton(R.string.btnSalvar, DialogInterface.OnClickListener { _, _ ->
                    viewModel.inserir(Inventario(0, v.txteNome.text.toString()))
                })
                .setNeutralButton(R.string.btnCancelar, null)
                .create()

        } ?: throw IllegalStateException("'activity' não pode ser nula!")



    }

}