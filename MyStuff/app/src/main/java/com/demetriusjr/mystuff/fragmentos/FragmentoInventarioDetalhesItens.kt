package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesItensBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarioDetalhesItens(vm:MyStuffViewModel):Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesItensBinding
    private val b get() = _b

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesItensBinding.inflate(inflater, container, false)
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
