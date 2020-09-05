package com.expandablenavigationview

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.expandablenavigationview.ui.adapters.ExpandableListAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity()
{

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_documents, R.id.nav_my_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        //region TODO Step
        val expandableListAdapter = ExpandableListAdapter(this)
        expandMenu.apply {
            setAdapter(expandableListAdapter)
            setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener{
                override fun onGroupClick(
                    expandView: ExpandableListView?,
                    p1: View?,
                    index: Int,
                    p3: Long
                ): Boolean {
                    when (index) {
                        0 -> {
                            nav_host_fragment.findNavController().navigate(R.id.nav_home)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                    }
                    return false
                }
            })
            setOnChildClickListener(object : ExpandableListView.OnChildClickListener{
                override fun onChildClick(
                    expandView: ExpandableListView?,
                    view: View?,
                    p2: Int,
                    p3: Int,
                    p4: Long
                ): Boolean {
                    when
                    {
                        p2 == 1 && p3 == 0 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_red_camera)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                        p2 == 1 && p3 == 1 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_green_camera)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                        p2 == 2 && p3 == 0 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_pictures)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                        p2 == 2 && p3 == 1 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_documents)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                        p2 == 3 && p3 == 0 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_my_slideshow)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                        p2 == 3 && p3 == 1 ->
                        {
                            nav_host_fragment.findNavController().navigate(R.id.nav_friends_slides)
                            drawerLayout.closeDrawer(GravityCompat.START, true)
                        }
                    }
                    return false
                }
            })
        }
        //endregion
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}