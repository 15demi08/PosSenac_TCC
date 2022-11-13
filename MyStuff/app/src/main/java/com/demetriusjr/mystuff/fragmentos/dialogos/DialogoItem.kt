package com.demetriusjr.mystuff.fragmentos.dialogos

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.CategoriaBinding
import com.demetriusjr.mystuff.databinding.DialogoBinding
import com.demetriusjr.mystuff.db.Categoria
import com.demetriusjr.mystuff.db.Item
import com.demetriusjr.mystuff.db.ItemComCategorias
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class DialogoItem(viewModel:MyStuffViewModel, inflater:LayoutInflater):Dialogo(viewModel) {

    private lateinit var listaLocais:List<Local>
    private lateinit var listaCategorias:List<Categoria>

    val localSelecionado = MutableLiveData<Local?>()
    val categoriasSelecionadas = MutableLiveData<List<Categoria>?>()

    private val flowIDs = ArrayList<Int>()

    init {

        dialogoBinding = DialogoBinding.inflate(inflater).apply {

            btnAumentarQuantidade.setOnClickListener {
                val txt = txteQuantidade.text.toString()
                if (txt == "") txteQuantidade.setText(numeroQuantidade(1))
                else txteQuantidade.setText(numeroQuantidade(txt.toInt() + 1))
            }

            btnDiminuirQuantidade.setOnClickListener {
                val txt = txteQuantidade.text.toString()
                if (txt != "") {
                    txt.toInt().let {
                        if (it > 0) txteQuantidade.setText(numeroQuantidade(it - 1))
                    }
                }
            }

            btnEscolherLocal.setOnClickListener {
                DialogoListaLocais(viewModel, this@DialogoItem, listaLocais).show(parentFragmentManager, "escolherLocal")
            }

            btnEscolherCategorias.setOnClickListener {
                var arrayListCategorias:ArrayList<Categoria>? = null
                if (categoriasSelecionadas.value != null)
                    arrayListCategorias = ArrayList<Categoria>().apply { addAll(categoriasSelecionadas.value!!) }
                DialogoListaCategorias(viewModel, this@DialogoItem, listaCategorias, arrayListCategorias).show(parentFragmentManager, "escolherCategorias")
            }

            btnRemoverLocalSelecionado.setOnClickListener { localSelecionado.value = null }
            btnRemoverCategoriasSelecionadas.setOnClickListener { categoriasSelecionadas.value = null }

        }

        idIcone = R.drawable.ic_round_item

        if (viewModel.itemSelecionado == null) {

            idTitulo = R.string.dialogoitemNovoTitulo

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
                viewModel.inserir(
                    ItemComCategorias(
                        Item(
                            0,
                            dialogoBinding.txteNome.text.toString(),
                            dialogoBinding.txteQuantidade.text.toString().toInt(),
                            viewModel.inventarioSelecionado!!.idInventario,
                            localSelecionado.value?.idLocal
                        ),
                        categoriasSelecionadas.value!!
                    )
                )
            }

        } else {

            idTitulo = R.string.dialogoItemEditarTitulo

            viewModel.itemSelecionado!!.let { itemSelecionado ->

                localSelecionado.value = itemSelecionado.local
                categoriasSelecionadas.value = itemSelecionado.categorias

                dialogoBinding.apply {
                    txteNome.setText(itemSelecionado.item.nome)
                    txteQuantidade.setText(itemSelecionado.item.quantidade.toString())
                }

            }

            positivoClickListener = DialogInterface.OnClickListener { _, _ ->
                viewModel.apply {
                    atualizar(ItemComCategorias(
                        itemSelecionado!!.item.copy(
                            nome = dialogoBinding.txteNome.text.toString(),
                            quantidade = dialogoBinding.txteQuantidade.text.toString().toInt(),
                            idLocal = this@DialogoItem.localSelecionado.value!!.idLocal
                        ),
                        categoriasSelecionadas.value!!
                    ))
                }
            }

        }

        idNeutro = R.string.btnCancelar

    }

    private fun numeroQuantidade(i:Int):String {
        return requireActivity().getString(R.string.dialogoItemQuantidadePlaceholder, i)
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.inventarioSelecionado!!.idInventario.let { idInv ->
            viewModel.locais(idInv).observe(this) { listaLocais = it }
            viewModel.categorias(idInv).observe(this) { listaCategorias = it }
        }

        localSelecionado.observe(this) { local ->
            dialogoBinding.apply {
                if (local == null) {
                    txtvEtiquetaLocal.text = getText(R.string.dialogoItemEtiquetaLocal)
                    btnRemoverLocalSelecionado.visibility = View.INVISIBLE
                    btnEscolherLocal.visibility = View.VISIBLE
                } else {
                    txtvEtiquetaLocal.text = local.nome.toString()
                    btnRemoverLocalSelecionado.visibility = View.VISIBLE
                    btnEscolherLocal.visibility = View.INVISIBLE
                }
            }
        }

        // Atenção: O código abaixo pode causar estranheza e talvez até mesmo repulsa em desenvolvedores Android experientes.
        // ...
        // Foi o que eu consegui fazer. Mal aí ¯\_(ツ)_/¯
        categoriasSelecionadas.observe(this) { categorias ->

            dialogoBinding.apply {

                CamposContainer.apply {
                    flowIDs.forEach {
                        removeView(findViewById(it))
                    }
                }

                flowIDs.removeAll(flowIDs.toSet())

                if (categorias == null || categorias.isEmpty()) {

                    btnRemoverCategoriasSelecionadas.visibility = View.INVISIBLE

                    flowCategoriasSelecionadas.apply {
                        visibility = View.GONE
                        referencedIds = IntArray(0)
                    }

                } else {

                    categoriasSelecionadas.value?.forEach { categoria ->

                        val categoriaBinding = CategoriaBinding.inflate(layoutInflater, dialogoBinding.CamposContainer, false).apply {
                            txtvCategoriaNome.text = categoria.nome
                            root.id = categoria.idCategoria.toInt()
                            flowIDs.add(root.id)
                        }
                        dialogoBinding.CamposContainer.addView(categoriaBinding.root)

                    }

                    flowCategoriasSelecionadas.apply {
                        referencedIds = IntArray(flowIDs.size) { flowIDs[it] }
                        visibility = View.VISIBLE
                    }

                    btnRemoverCategoriasSelecionadas.visibility = View.VISIBLE

                }
            }
        }

    }

    override fun onDismiss(dialog:DialogInterface) {
        super.onDismiss(dialog)
        viewModel.itemSelecionado = null
    }

}