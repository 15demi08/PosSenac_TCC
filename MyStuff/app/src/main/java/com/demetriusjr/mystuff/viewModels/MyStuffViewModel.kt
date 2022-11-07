package com.demetriusjr.mystuff.viewModels

import androidx.lifecycle.*
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.db.Local
import com.demetriusjr.mystuff.db.Repositorio
import kotlinx.coroutines.launch

class MyStuffViewModel(private val repositorio:Repositorio):ViewModel() {

    var objetoSelecionadoOpcoes:Any? = null // P/ menus

    // Inventário
    var inventarioSelecionado:Inventario? = null
    val inventarios:LiveData<List<Inventario>> = repositorio.inventarios.asLiveData()
    fun inserir(inventario:Inventario) = viewModelScope.launch { repositorio.inserir(inventario) }
    fun atualizar(inventario:Inventario) = viewModelScope.launch { repositorio.atualizar(inventario) }
    fun excluir(inventario:Inventario) = viewModelScope.launch { repositorio.excluir(inventario) }

    // Local
    var localSelecionado:Local? = null
    fun locais(idInventario:Long):LiveData<List<Local>> = repositorio.locais(idInventario).asLiveData()
    fun inserir(local:Local) = viewModelScope.launch { repositorio.inserir(local) }
    fun atualizar(local:Local) = viewModelScope.launch { repositorio.atualizar(local) }
    fun excluir(local:Local) = viewModelScope.launch { repositorio.excluir(local) }

}

class MyStuffViewModelFactory(private val repositorio:Repositorio):ViewModelProvider.Factory {

    override fun <T:ViewModel> create(modelClass:Class<T>):T {
        if (modelClass.isAssignableFrom(MyStuffViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyStuffViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}