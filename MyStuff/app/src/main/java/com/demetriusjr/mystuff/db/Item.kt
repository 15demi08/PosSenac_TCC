package com.demetriusjr.mystuff.db

import androidx.room.*
import androidx.room.ForeignKey.*
import kotlinx.coroutines.flow.Flow

@Entity(
    foreignKeys = [
        ForeignKey(entity = Local::class, parentColumns = ["idLocal"], childColumns = ["idLocal"], onDelete = SET_NULL),
        ForeignKey(entity = Inventario::class, parentColumns = ["idInventario"], childColumns = ["idInventario"], onDelete = CASCADE)
    ]
)
data class Item (
    @PrimaryKey(autoGenerate = true) val idItem:Long,
    val nome:String?,
    val quantidade:Long,
    val idInventario:Long,
    val idLocal:Long?
)

@Dao
interface ItemDAO:DB.BaseDAO<Item> {

    // Todos os Items no Inventário
    @Transaction
    @Query("SELECT * FROM item WHERE idInventario = :idInventario")
    fun consultar(idInventario:Long):Flow<List<ItemComLocalCategorias>>

    // Items de Um Local
    @Transaction
    @Query("SELECT * FROM item WHERE idLocal = :idLocal")
    fun consultarPorLocal(idLocal:Long):Flow<List<ItemComCategorias>>

}