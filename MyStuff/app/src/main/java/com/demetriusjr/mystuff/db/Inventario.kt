package com.demetriusjr.mystuff.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Inventario (
    @PrimaryKey(autoGenerate = true) val idInventario:Long,
    val nome:String?
)

@Dao
interface InventarioDAO {

    @Insert
    suspend fun inserir(vararg inventarios:Inventario)

    @Update
    suspend fun atualizar(inventario:Inventario)

    @Delete
    suspend fun excluir(inventario:Inventario)

    @Query("SELECT * FROM inventario")
    fun consultar():Flow<List<Inventario>>

}