package com.demetriusjr.mystuff.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Inventario::class, Local::class, Categoria::class, Item::class, ItemCategoria::class], version = 1, exportSchema = false)
abstract class DB:RoomDatabase() {
    
    abstract fun inventarioDAO():InventarioDAO
    abstract fun localDAO():LocalDAO
    abstract fun categoriaDAO():CategoriaDAO
    abstract fun itemDAO():ItemDAO

    companion object {

        @Volatile
        private var INSTANCE:DB? = null

        // escopo:CoroutineScope -> Receber como parâmetro no caso de haver callbacks na criação do DB
        fun construir(contexto:Context /*, escopo*/):DB {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    contexto.applicationContext,
                    DB::class.java,
                    "mystuff"
                ).build()
                INSTANCE = instance
                instance

            }

        }

    }
    
}