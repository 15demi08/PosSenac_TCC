package com.demetriusjr.mystuff.viewModels

import androidx.lifecycle.*
import com.demetriusjr.mystuff.db.*
import kotlinx.coroutines.launch

class MyStuffViewModel(private val repositorio:Repositorio):ViewModel() {

    // Inventário
    var inventarioSelecionado:Inventario? = null
    val inventarios:LiveData<List<Inventario>> = repositorio.inventarios.asLiveData()

    // Local
    var localSelecionado:Local? = null
    fun locais(idInventario:Long):LiveData<List<Local>> = repositorio.locais(idInventario).asLiveData()
    fun quantidadeLocais(idInventario:Long):LiveData<Int> = repositorio.quantidadeLocais(idInventario).asLiveData()

    // Categoria
    var categoriaSelecionada:Categoria? = null
    fun categorias(idInventario:Long):LiveData<List<Categoria>> = repositorio.categorias(idInventario).asLiveData()
    fun quantidadeCategorias(idInventario:Long):LiveData<Int> = repositorio.quantidadeCategorias(idInventario).asLiveData()

    // Item
    var podeIncluirItens:Boolean = false
    var itemSelecionado:ItemComLocalCategorias? = null
    fun itens(idInventario:Long):LiveData<List<ItemComLocalCategorias>> = repositorio.itensPorInventario(idInventario).asLiveData()

    fun inserir(obj:Any) {
        when(obj){
            is Inventario -> viewModelScope.launch { repositorio.inserir(obj) }
            is Local -> viewModelScope.launch { repositorio.inserir(obj) }
            is Categoria -> viewModelScope.launch { repositorio.inserir(obj) }
            is Item -> viewModelScope.launch { repositorio.inserir(obj) }
            is ItemComCategorias -> viewModelScope.launch {
                val idItem = repositorio.inserir(obj.item)
                val relacoes = ArrayList<ItemCategoria>().apply {
                    obj.categorias.forEach {
                        add(ItemCategoria(idItem, it.idCategoria))
                    }
                }
                repositorio.inserirVarios(*Array<ItemCategoria>(relacoes.size){ relacoes[it] })
            }
        }
    }

    fun atualizar(obj:Any) {
        when(obj){
            is Inventario -> viewModelScope.launch { repositorio.atualizar(obj) }
            is Local -> viewModelScope.launch { repositorio.atualizar(obj) }
            is Categoria -> viewModelScope.launch { repositorio.atualizar(obj) }
            is Item -> viewModelScope.launch { repositorio.atualizar(obj) }
            is ItemComCategorias -> viewModelScope.launch {
                repositorio.atualizar(obj.item) // Atualizar dados do Item (nome, qtde, idLocal)
                repositorio.excluirRelacoesDoItem(obj.item.idItem) // Excluir relações para o idItem na ItemCategoria
                val relacoes = ArrayList<ItemCategoria>().apply {
                    obj.categorias.forEach {
                        add(ItemCategoria(obj.item.idItem, it.idCategoria))
                    }
                }
                repositorio.inserirVarios(*Array<ItemCategoria>(relacoes.size){ relacoes[it] }) // Incluir novas relações
            }
        }
    }

    fun excluir(obj:Any) {
        when(obj){
            is Inventario -> viewModelScope.launch { repositorio.excluir(obj) }
            is Local -> viewModelScope.launch { repositorio.excluir(obj) }
            is Categoria -> viewModelScope.launch { repositorio.excluir(obj) }
            is Item -> viewModelScope.launch { repositorio.excluir(obj) }
            is ItemCategoria -> viewModelScope.launch { repositorio.excluir(obj) }
            is ItemComLocalCategorias -> viewModelScope.launch { repositorio.excluir(obj.item) }
        }
    }

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