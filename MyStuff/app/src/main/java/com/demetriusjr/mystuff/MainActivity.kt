package com.demetriusjr.mystuff

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.demetriusjr.mystuff.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity:AppCompatActivity() {

    private lateinit var abc:AppBarConfiguration

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

        val b = ActivityMainBinding.inflate(layoutInflater)
        //val nc = b.navHost.findNavController()
        val nc = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        abc = AppBarConfiguration(nc.graph)

        setContentView(b.root)
        setSupportActionBar(b.appBar)
        NavigationUI.setupActionBarWithNavController(this, nc, abc)

    }

    override fun onSupportNavigateUp():Boolean {
        val navController = findNavController(R.id.navHost)
        return navController.navigateUp(abc) || super.onSupportNavigateUp()
    }

    fun mostrarSnackbar(view:View, idString:Int) = Snackbar.make(view, idString, Snackbar.LENGTH_SHORT).setTextMaxLines(2).show()

}