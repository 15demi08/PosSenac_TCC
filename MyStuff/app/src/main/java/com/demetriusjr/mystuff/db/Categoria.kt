package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Categoria(
    @PrimaryKey(autoGenerate = true) val idCategoria:Long,
    val nome:String?
)

@Dao
interface CategoriaDAO {

    @Insert
    suspend fun inserir(vararg categorias:Categoria)

    @Update
    suspend fun atualizar(categoria:Categoria)

    @Delete
    suspend fun excluir(categoria:Categoria)

    @Query("SELECT * FROM categoria")
    suspend fun consultar():List<Categoria>

    @Query("SELECT * FROM categoria WHERE idCategoria = :idCategoria")
    suspend fun consultar(idCategoria:Long):Categoria

    @Transaction
    @Query("SELECT * FROM categoria WHERE idCategoria= :idCategoria")
    suspend fun consultarComItens(idCategoria:Long):CategoriaComItens

}