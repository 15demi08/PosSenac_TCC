package com.demetriusjr.mystuff.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.coroutines.flow.Flow

@Entity(
    foreignKeys = [
        ForeignKey(entity = Inventario::class, parentColumns = ["idInventario"], childColumns = ["idInventario"], onDelete = CASCADE)
    ]
)
data class Categoria(
    @PrimaryKey(autoGenerate = true) val idCategoria:Long,
    val nome:String,
    val idInventario:Long
)

@Dao
interface CategoriaDAO:DB.BaseDAO<Categoria> {

    @Query("SELECT * FROM categoria WHERE idInventario = :idInventario ORDER BY nome ASC")
    fun consultar(idInventario:Long):Flow<List<Categoria>>

    @Query("SELECT COUNT(idCategoria) FROM categoria WHERE idInventario = :idInventario")
    fun quantidadeCategorias(idInventario:Long):Flow<Int>

    // @Transaction
    // @Query("SELECT * FROM categoria WHERE idCategoria = :idCategoria")
    // fun consultarComItensLocais(idCategoria:Long):CategoriaComItensLocais

    // @Query("SELECT * FROM categoria " +
    //         "JOIN itemcategoria USING(idCategoria) " +
    //         "JOIN item USING(idItem) " +
    //         "JOIN local USING(idLocal) " +
    //         "WHERE idCategoria = :idCategoria")
    // fun consultarItensLocais(idCategoria:Long):Map<Item, Local>

}