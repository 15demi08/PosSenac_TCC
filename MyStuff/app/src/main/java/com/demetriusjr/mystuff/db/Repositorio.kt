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

}