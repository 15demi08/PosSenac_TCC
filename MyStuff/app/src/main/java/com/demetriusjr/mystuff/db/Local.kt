package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Local(
    @PrimaryKey val id:Int,
    val nome:String?,
    val idInventario:Int
)

@Dao
interface LocalDAO {

    @Insert
    fun inserir(vararg local:Local)

    @Update
    fun atualizar(local:Local)

    @Delete
    fun excluir(local:Local)

    @Query("SELECT * FROM local")
    fun consultarTodos():List<Local>

    @Query("SELECT * FROM local WHERE idInventario = :idInventario")
    fun consultarPorInventario(idInventario:Int):List<Local>

}