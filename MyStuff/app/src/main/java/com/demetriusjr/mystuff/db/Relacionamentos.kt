package com.demetriusjr.mystuff.db

import androidx.room.*

/**
 * Item (n)<->(n) Categoria
 */

@Entity(primaryKeys = ["idItem", "idCategoria"])
data class ItemCategoria (
    val idItem:Long,
    val idCategoria:Long
)

@Dao
interface ItemCategoriaDAO {

    @Insert
    suspend fun inserir(vararg itemCategoria:ItemCategoria)

    @Update
    suspend fun atualizar(itemCategoria:ItemCategoria)

    @Delete
    suspend fun excluir(itemCategoria:ItemCategoria)

}

data class CategoriaComItens (
    @Embedded val categoria:Categoria,
    @Relation(
        parentColumn = "idCategoria",
        entityColumn = "idItem",
        associateBy = Junction(ItemCategoria::class)
    )
    val itens:List<Item>
)

data class ItemComCategorias (
    @Embedded val item:Item,
    @Relation(
        parentColumn = "idItem",
        entityColumn = "idCategoria",
        associateBy = Junction(ItemCategoria::class)
    )
    val categorias:List<Categoria>
)