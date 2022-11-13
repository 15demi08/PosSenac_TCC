package com.demetriusjr.mystuff.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.coroutines.flow.Flow

@Entity(
    foreignKeys = [
        ForeignKey(entity = Inventario::class, parentColumns = ["idInventario"], childColumns = ["idInventario"], onDelete = CASCADE)
    ]
)
data class Local (
    @PrimaryKey(autoGenerate = true) val idLocal:Long,
    val nome:String,
    val idInventario:Long
)

@Dao
interface LocalDAO:DB.BaseDAO<Local> {

    @Query("SELECT * FROM local WHERE idInventario = :idInventario ORDER BY nome ASC")
    fun consultar(idInventario:Long):Flow<List<Local>>

    @Query("SELECT COUNT(idLocal) FROM local WHERE idInventario = :idInventario")
    fun quantidadeLocais(idInventario:Long):Flow<Int>

}