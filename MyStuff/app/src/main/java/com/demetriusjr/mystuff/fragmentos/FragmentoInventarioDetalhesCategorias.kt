package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesCategoriasBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarioDetalhesCategorias(vm:MyStuffViewModel):Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesCategoriasBinding
    private val b get() = _b

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesCategoriasBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    companion object {
        @JvmStatic
        fun newInstance(vm:MyStuffViewModel) = FragmentoInventarioDetalhesLocais(vm)
    }

}
