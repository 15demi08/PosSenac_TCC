package com.demetriusjr.mystuff

import android.app.Application
import com.demetriusjr.mystuff.db.DB
import com.demetriusjr.mystuff.db.Repositorio

class MyStuffApplication:Application() {

    // val escopo = CoroutineScope(SupervisorJob()) -> Se for necessário realizar callbacks na criação
    //                                                 do DB (antes do .build())

    val db by lazy { DB.construir(applicationContext /*, escopo*/ ) }
    val repositorio by lazy {
        Repositorio(
            db.inventarioDAO(),
            db.localDAO(),
            db.itemDAO(),
            db.categoriaDAO()
        )
    }

}