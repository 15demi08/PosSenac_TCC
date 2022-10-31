package com.demetriusjr.mystuff.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Inventario::class, Local::class, Categoria::class, Item::class], version = 1)
abstract  class DB:RoomDatabase() {
    abstract fun inventarioDAO():InventarioDAO
    abstract fun localDAO():LocalDAO
    abstract fun categoriaDAO():CategoriaDAO
    abstract fun itemDAO():ItemDAO
}