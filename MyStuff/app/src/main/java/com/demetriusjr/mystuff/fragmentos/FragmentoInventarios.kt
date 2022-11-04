package com.demetriusjr.mystuff.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demetriusjr.mystuff.MainActivity
import com.demetriusjr.mystuff.MyStuffApplication
import com.demetriusjr.mystuff.R
import com.demetriusjr.mystuff.databinding.FragmentoInventariosBinding
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.fragmentos.utilidades.InventariosAdapter
import com.demetriusjr.mystuff.viewModels.MyStuffViewModel
import com.demetriusjr.mystuff.viewModels.MyStuffViewModelFactory

class FragmentoInventarios : Fragment() {

    private lateinit var _b:FragmentoInventariosBinding
    private val b get() = _b

    private lateinit var app:MyStuffApplication
    private val viewModel:MyStuffViewModel by navGraphViewModels(R.id.navegacao){ MyStuffViewModelFactory(app.repositorio) }

    override fun onAttach(context:Context) {
        super.onAttach(context)
        app = activity?.application as MyStuffApplication
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentoInventariosBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {
            novoInventario.setOnClickListener { novoInventario() }
            rclvListaInventarios.apply {
                layoutManager = LinearLayoutManager(this@FragmentoInventarios.requireContext())
                adapter = InventariosAdapter()
            }
        }

        viewModel.inventarios.observe(viewLifecycleOwner, Observer {
            (b.rclvListaInventarios.adapter as InventariosAdapter).submitList(it)
        })

    }

    fun novoInventario(){
        findNavController().navigate(R.id.action_fragmentoInventarios_to_fragmentoInventarioDetalhes)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentoInventarios()
    }

}
