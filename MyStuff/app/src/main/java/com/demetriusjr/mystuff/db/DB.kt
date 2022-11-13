package com.demetriusjr.mystuff.db

import android.content.Context
import androidx.room.*

@Database(entities = [Inventario::class, Local::class, Categoria::class, Item::class, ItemCategoria::class], version = 7, exportSchema = false)
abstract class DB:RoomDatabase() {

    interface BaseDAO<T> {
        @Insert suspend fun inserir(obj:T):Long
        @Update suspend fun atualizar(obj:T)
        @Delete suspend fun excluir(obj:T)
    }

    abstract fun inventarioDAO():InventarioDAO
    abstract fun localDAO():LocalDAO
    abstract fun categoriaDAO():CategoriaDAO
    abstract fun itemDAO():ItemDAO
    abstract fun itemCategoriaDAO():ItemCategoriaDAO

    companion object {

        @Volatile
        private var INSTANCE:DB? = null

        // escopo:CoroutineScope -> Receber como parâmetro no caso de haver callbacks na criação do DB
        fun construir(contexto:Context /*, escopo:CoroutineScope*/):DB {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(contexto.applicationContext, DB::class.java, "mystuff")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance

            }

        }

    }

}