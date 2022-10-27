package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Item(
    @PrimaryKey val id:Int,
    val nome:String?,
    val quantidade:Int,
    val idLocal:Local
)

@Dao
interface ItemDAO {

    @Insert
    fun inserir(vararg itens:Item)

    @Update
    fun atualizar(item:Item)

    @Delete
    fun excluir(item:Item)

    @Query("SELECT * FROM item")
    fun consultarTodos():List<Item>

    @Query("SELECT * FROM item WHERE idLocal = :idLocal")
    fun consultarPorLocal(idLocal:Int):List<Item>

}