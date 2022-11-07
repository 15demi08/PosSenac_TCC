package com.demetriusjr.mystuff.db

import kotlinx.coroutines.flow.Flow

class Repositorio(
    private val inventarioDAO:InventarioDAO,
    private val localDAO:LocalDAO,
    private val itemDAO:ItemDAO,
    private val categoriaDAO:CategoriaDAO
) {

    // Inventário
    val inventarios:Flow<List<Inventario>> = inventarioDAO.consultar()
    suspend fun inserir(vararg inventarios:Inventario) = inventarioDAO.inserir(*inventarios)
    suspend fun atualizar(inventario:Inventario) = inventarioDAO.atualizar(inventario)
    suspend fun excluir(inventario:Inventario) = inventarioDAO.excluir(inventario)

    // Local
    fun locais(idInventario:Long):Flow<List<Local>> = localDAO.consultarTodos(idInventario)
    suspend fun local(idLocal:Long):Local = localDAO.consultarUm(idLocal)
    suspend fun inserir(vararg locais:Local) = localDAO.inserir(*locais)
    suspend fun atualizar(local:Local) = localDAO.atualizar(local)
    suspend fun excluir(local:Local) = localDAO.excluir(local)

}