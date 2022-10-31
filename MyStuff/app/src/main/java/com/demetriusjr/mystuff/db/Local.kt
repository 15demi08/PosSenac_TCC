package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Local (
    @PrimaryKey(autoGenerate = true) val idLocal:Long,
    val nome:String?,
    val idInventario:Long
)

@Dao
interface LocalDAO {

    @Insert
    suspend fun inserir(vararg local:Local)

    @Update
    suspend fun atualizar(local:Local)

    @Delete
    suspend fun excluir(local:Local)

    @Query("SELECT * FROM local WHERE idLocal = :idLocal")
    suspend fun consultar(idLocal:Long):Local

    @Query("SELECT * FROM local JOIN inventario USING(idInventario)")
    suspend fun consultar():List<Local>

}