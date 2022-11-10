package com.demetriusjr.mystuff.fragmentos.utilidades

import android.view.View

// BaseAdapterClickListener
interface BACL<T> {
    fun onClick(obj:T)
    fun onBtnClick(v:View, obj:T)
}