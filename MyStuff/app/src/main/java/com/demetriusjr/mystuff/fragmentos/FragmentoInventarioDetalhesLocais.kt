package com.demetriusjr.mystuff.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesLocaisBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarioDetalhesLocais( val viewModel:MyStuffViewModel ):Fragment() {

    private lateinit var _b:FragmentoInventarioDetalhesLocaisBinding
    private val b get() = _b

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View {
        _b = FragmentoInventarioDetalhesLocaisBinding.inflate(inflater, container, false)
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