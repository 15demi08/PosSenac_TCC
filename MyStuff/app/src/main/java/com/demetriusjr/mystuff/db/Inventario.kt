package com.demetriusjr.mystuff.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Inventario (
    @PrimaryKey(autoGenerate = true) val idInventario:Long,
    val nome:String
)

@Dao
interface InventarioDAO:DB.BaseDAO<Inventario> {

    @Query("SELECT * FROM inventario ORDER BY nome ASC")
    fun consultar():Flow<List<Inventario>>

}