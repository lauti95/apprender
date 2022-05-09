package com.example.firebaselogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firebaselogin.databinding.ActivityDrawerBinding
import com.google.android.material.navigation.NavigationView
import androidx.constraintlayout.widget.ConstraintLayout

class DrawerActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrawerBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicialización de toolbar
        setSupportActionBar(binding.appBarMain.myToolbar)
        //Inicialización variables
        navView = binding.navigationView
        drawerLayout = binding.drawer
        navController = findNavController(R.id.nav_host_container_view)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navCurso, R.id.navAcercaDe, R.id.navPerfil, R.id.navReporte
            ), drawerLayout
        )
        //Ejecución de navController y navView:
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
       val navController = findNavController(R.id.nav_host_container_view)
       return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}