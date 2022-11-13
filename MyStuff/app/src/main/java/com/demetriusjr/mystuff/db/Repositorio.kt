package com.demetriusjr.mystuff.db

import kotlinx.coroutines.flow.Flow

class Repositorio(
    private val inventarioDAO:InventarioDAO,
    private val localDAO:LocalDAO,
    private val itemDAO:ItemDAO,
    private val categoriaDAO:CategoriaDAO,
    private val itemCategoriaDAO:ItemCategoriaDAO
) {

    // Inventário
    val inventarios:Flow<List<Inventario>> = inventarioDAO.consultar()
    suspend fun inserir(inventario:Inventario) = inventarioDAO.inserir(inventario)
    suspend fun atualizar(inventario:Inventario) = inventarioDAO.atualizar(inventario)
    suspend fun excluir(inventario:Inventario) = inventarioDAO.excluir(inventario)

    // Local
    fun locais(idInventario:Long):Flow<List<Local>> = localDAO.consultar(idInventario)
    fun quantidadeLocais(idInventario:Long):Flow<Int> = localDAO.quantidadeLocais(idInventario)
    suspend fun inserir(local:Local) = localDAO.inserir(local)
    suspend fun atualizar(local:Local) = localDAO.atualizar(local)
    suspend fun excluir(local:Local) = localDAO.excluir(local)

    // Categoria
    fun categorias(idInventario:Long):Flow<List<Categoria>> = categoriaDAO.consultar(idInventario)
    fun quantidadeCategorias(idInventario:Long):Flow<Int> = categoriaDAO.quantidadeCategorias(idInventario)
    suspend fun inserir(categoria:Categoria) = categoriaDAO.inserir(categoria)
    suspend fun atualizar(categoria:Categoria) = categoriaDAO.atualizar(categoria)
    suspend fun excluir(categoria:Categoria) = categoriaDAO.excluir(categoria)

    // Item
    fun itensPorInventario(idInventario:Long):Flow<List<ItemComLocalCategorias>> = itemDAO.consultar(idInventario)
    fun itensPorLocal(idLocal:Long):Flow<List<ItemComCategorias>> = itemDAO.consultarPorLocal(idLocal)
    suspend fun inserir(item:Item) = itemDAO.inserir(item)
    suspend fun atualizar(item:Item) = itemDAO.atualizar(item)
    suspend fun excluir(item:Item) = itemDAO.excluir(item)

    // ItemCategoria
    suspend fun inserirVarios(vararg itemCategoria:ItemCategoria) = itemCategoriaDAO.inserir(*itemCategoria)
    suspend fun atualizar(itemCategoria:ItemCategoria) = itemCategoriaDAO.atualizar(itemCategoria)
    suspend fun excluir(itemCategoria:ItemCategoria) = itemCategoriaDAO.excluir(itemCategoria)
    suspend fun excluirRelacoesDoItem(idItem:Long) = itemCategoriaDAO.excluirRelacoesDoItem(idItem)

}