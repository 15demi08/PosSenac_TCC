package com.demetriusjr.mystuff.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun consultarUm(idLocal:Long):Local

    @Query("SELECT * FROM local WHERE idInventario = :idInventario")
    fun consultarTodos(idInventario:Long):Flow<List<Local>>

}