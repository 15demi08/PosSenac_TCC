package com.demetriusjr.mystuff.fragmentos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventarioDetalhesBinding
import com.demetriusjr.mystuff.databinding.FragmentoInventariosBinding
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel

class FragmentoInventarios : Fragment() {

    private lateinit var _b:FragmentoInventariosBinding
    private val b get() = _b

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentoInventariosBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.novoInventario.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_fragmentoInventarioDetalhes) }

    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarios()
    }

}