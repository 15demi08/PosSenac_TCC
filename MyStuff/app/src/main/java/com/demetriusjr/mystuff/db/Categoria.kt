package com.demetriusjr.mystuff.db

import androidx.room.*

@Entity
data class Categoria (
    @PrimaryKey val id:Int,
    val nome:String?
)

@Dao
interface CategoriaDAO {

    @Insert
    fun inserir( vararg categorias: Categoria )

    @Update
    fun atualizar(categoria: Categoria)

    @Delete
    fun excluir(categoria: Categoria)

    @Query("SELECT * FROM categoria")
    fun consultarTodos(): List<Categoria>

}