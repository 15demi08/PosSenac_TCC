package com.demetriusjr.mystuff.fragmentos.dialogos

import android.app.Dialog
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.demetriusjr.mystuff.MainActivity
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

open class Dialogo(val viewModel:MyStuffViewModel):DialogFragment() {

    protected lateinit var dialogoBinding:DialogoBinding
    protected lateinit var builder:AlertDialog.Builder

    protected var validoNome = MutableLiveData(false)
    protected var validoQuantidade = MutableLiveData(false)

    private val nomeChangeListener = object:TextWatcher {
        override fun beforeTextChanged(texto:CharSequence?, p1:Int, p2:Int, p3:Int) {  }
        override fun onTextChanged(texto:CharSequence?, p1:Int, p2:Int, p3:Int) {
            validoNome.value = !(texto == null || (texto.isBlank() || texto.isEmpty() || texto.toString() == ""))
        }
        override fun afterTextChanged(texto:Editable?) {  }
    }

    protected val quantidadeChangeListener = object:TextWatcher {
        override fun beforeTextChanged(texto:CharSequence?, p1:Int, p2:Int, p3:Int) {  }
        override fun onTextChanged(texto:CharSequence?, p1:Int, p2:Int, p3:Int) {
            var r = false
            r = if ( ( (texto == null) || (texto.isBlank() || texto.isEmpty() ) ) ) false
                else texto.toString().toInt() >= 1
            validoQuantidade.value = r
        }
        override fun afterTextChanged(texto:Editable?) {  }
    }

    protected var idTitulo:Int = 0
    protected var idIcone:Int = 0

    protected var visibilidadeCamposAdicionais:Int = View.VISIBLE

    protected lateinit var positivoClickListener:OnClickListener

    protected var idNeutro:Int = 0
    protected var neutroClickListener:OnClickListener? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        dialogoBinding.CamposContainer.visibility = visibilidadeCamposAdicionais

        dialogoBinding.txteNome.addTextChangedListener(nomeChangeListener)
        observarNome()

        builder = AlertDialog.Builder(requireActivity())
            .setTitle(idTitulo)
            .setIcon(idIcone)
            .setView(dialogoBinding.root)
            .setPositiveButton(R.string.btnSalvar, positivoClickListener)
            .setNeutralButton(idNeutro, neutroClickListener)

    }

    override fun onCreateDialog(savedInstanceState:Bundle?):Dialog = builder.create()

    protected fun mostrarSnackBar(msg:Int){
        val a = (requireActivity() as MainActivity)
        a.mostrarSnackbar(a.findViewById(R.id.container), msg)
    }

    /**
     * "His GAMBIARRA level is over 9000!"
     */
    protected fun configurarCampoNome(texto:String){
        dialogoBinding.txteNome.apply {
            setText(texto)
            val t = this.text.toString() + " "
            setText(t)
            setText(t.trim())
        }
    }

    protected fun observarNome(){
        validoNome.observe(this) {
            if(!it){
                dialogoBinding.txtiNome.error = getText(R.string.msgDialogoNomeVazio)
            } else {
                dialogoBinding.txtiNome.error = null
            }
        }
    }

    protected fun observarQuantidade(){
        validoQuantidade.observe(this) {
            println(it)
            if(!it){
                dialogoBinding.txtiQuantidade.error = getText(R.string.msgDialogoQuantidadeVaziaOuZero)
            } else {
                dialogoBinding.txtiQuantidade.error = null
            }
        }
    }

}