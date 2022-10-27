package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Inventario(
    @PrimaryKey val id: Int,
    val nome: String?
)

@Dao
interface InventarioDAO {

    @Insert
    fun inserir(vararg inventarios: Inventario)

    @Update
    fun atualizar(inventario: Inventario)

    @Delete
    fun excluir(inventario: Inventario)

    @Query("SELECT * FROM inventario")
    fun consultarTodos(): List<Inventario>

}