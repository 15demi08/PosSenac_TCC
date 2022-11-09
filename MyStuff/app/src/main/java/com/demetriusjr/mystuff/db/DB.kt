package com.demetriusjr.mystuff.db

import android.content.Context
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Database(entities = [Inventario::class, Local::class, Categoria::class, Item::class, ItemCategoria::class], version = 3, exportSchema = false)
abstract class DB:RoomDatabase() {

    interface BaseDAO<T> {
        @Insert suspend fun inserir(vararg objs:T)
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
        fun construir(contexto:Context /*, escopo*/):DB {

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