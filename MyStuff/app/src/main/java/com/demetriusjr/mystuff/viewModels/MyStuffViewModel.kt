package com.demetriusjr.mystuff.viewModels

import androidx.lifecycle.*
import com.demetriusjr.mystuff.db.Inventario
import com.demetriusjr.mystuff.db.Repositorio
import kotlinx.coroutines.launch

class MyStuffViewModel(
    private val repositorio:Repositorio
):ViewModel() {

    // Inventário
    var inventarioSelecionado:Inventario? = null
    val inventarios:LiveData<List<Inventario>> = repositorio.inventarios.asLiveData()
    fun insert(inventario:Inventario) = viewModelScope.launch { repositorio.inserir(inventario) }
    fun atualizar(inventario:Inventario) = viewModelScope.launch { repositorio.atualizar(inventario) }
    fun excluir(inventario:Inventario) = viewModelScope.launch { repositorio.excluir(inventario) }


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