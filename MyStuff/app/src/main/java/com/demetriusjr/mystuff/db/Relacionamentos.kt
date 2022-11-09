package com.demetriusjr.mystuff.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * Item (1)<->(1) Local
 */
data class ItemLocal(
    @Embedded
    val item:Item,
    @Relation(parentColumn = "idLocal", entityColumn = "idLocal")
    val local:Local
)


/**
 * Item (n)<->(n) Categoria
 */

@Entity(
    primaryKeys = ["idItem", "idCategoria"],
    foreignKeys = [
        ForeignKey(entity = Item::class, parentColumns = ["idItem"], childColumns = ["idItem"], onDelete = CASCADE),
        ForeignKey(entity = Categoria::class, parentColumns = ["idCategoria"], childColumns = ["idCategoria"], onDelete = CASCADE)
    ]
)
data class ItemCategoria(
    val idItem:Long,
    val idCategoria:Long
)

@Dao
interface ItemCategoriaDAO {
    @Insert suspend fun inserir(vararg itemCategoria:ItemCategoria)
    @Update suspend fun atualizar(itemCategoria:ItemCategoria)
    @Delete suspend fun excluir(itemCategoria:ItemCategoria)
}

data class CategoriaComItens(
    @Embedded val categoria:Categoria,
    @Relation(parentColumn = "idCategoria", entityColumn = "idItem", associateBy = Junction(ItemCategoria::class))
    val itens:List<Item>
)

data class ItemComCategorias(
    @Embedded val item:Item,
    @Relation(parentColumn = "idItem",entityColumn = "idCategoria",associateBy = Junction(ItemCategoria::class))
    val categorias:List<Categoria>
)

/**
 * Local (1)<->(n) Item (n)<->(n) Categoria
 */
data class ItemComLocalCategorias(
    @Embedded val item:Item,
    @Relation(parentColumn = "idLocal", entityColumn = "idLocal")
    val local:Local,
    @Relation(parentColumn = "idItem",entityColumn = "idCategoria",associateBy = Junction(ItemCategoria::class))
    val categorias:List<Categoria>
)

data class CategoriaComItensLocais(
    @Embedded val categoria:Categoria,
    @Relation(parentColumn = "idCategoria", entityColumn = "idItem", associateBy = Junction(ItemCategoria::class))
    val itemLocal:List<ItemLocal>
)