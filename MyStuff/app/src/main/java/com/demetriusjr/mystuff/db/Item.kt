package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Item (
    @PrimaryKey(autoGenerate = true) val idItem:Long,
    val nome:String?,
    val quantidade:Long,
    val idLocal:Local
)

@Dao
interface ItemDAO {

    @Insert
    suspend fun inserir(vararg itens:Item)

    @Update
    suspend fun atualizar(item:Item)

    @Delete
    suspend fun excluir(item:Item)

    @Query("SELECT * FROM item JOIN local USING(idLocal) WHERE idLocal = :idLocal")
    suspend fun consultarPorLocal(idLocal:Long):List<Item>

    @Transaction
    @Query("SELECT * FROM item WHERE idItem = :idItem")
    suspend fun consultarComCategorias(idItem:Long):ItemComCategorias

}