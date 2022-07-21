package com.example.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.bookhub.*
import com.example.bookhub.databinding.ActivityMainBinding
import com.example.bookhub.fragment.AboutAppFragment
import com.example.bookhub.fragment.DashBoardFragment
import com.example.bookhub.fragment.FavouritesFragment
import com.example.bookhub.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding

var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        openDashBoard()

        binding.navigatorView.setNavigationItemSelectedListener {

            if(previousMenuItem!=null){

                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true

            it.isChecked = true

            previousMenuItem = it

            when(it.itemId){

                R.id.dashboard -> {

                    openDashBoard()
                    binding.drawerLayout.closeDrawers()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())

                        .commit()
                    supportActionBar?.title = "Favourites"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())

                        .commit()
                    supportActionBar?.title = "Profile"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutAppFragment())

                        .commit()
                    supportActionBar?.title = "About App"
                    binding.drawerLayout.closeDrawers()
            }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    private fun setUpToolbar(){

        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = "Toolbar Title"

        supportActionBar?.setHomeButtonEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == android.R.id.home){

            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashBoard(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, DashBoardFragment())
            .addToBackStack("Dashboard")
            .commit()

        supportActionBar?.title = "Dashboard"

        binding.navigatorView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {

        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){

            !is DashBoardFragment -> openDashBoard()

            else -> super.onBackPressed()
        }

    }
}