package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.View

// BaseAdapterClickListener
interface BACL<T> {
    // Clique no item da lista
    fun onClick(v:View, obj:T)
    // Clique em dos botões de opções do item da lista (editar e excluir)
    fun onBtnClick(v:View, obj:T)
}