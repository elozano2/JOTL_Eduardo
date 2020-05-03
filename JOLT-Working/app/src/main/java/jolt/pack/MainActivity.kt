package jolt.pack

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupNavigation()

        val navController = findNavController (R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.botNav)
        bottomNav?.setupWithNavController(navController)

        val context : Context = MainActivity.applicationContext()


    }

   // private fun setupNavigation(){}

}