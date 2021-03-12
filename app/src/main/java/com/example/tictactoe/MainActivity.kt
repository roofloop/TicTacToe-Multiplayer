package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth



val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when(item.itemId) {

        R.id.home -> {
            // Respond to navigation item 1 click
            Log.e("TAG", "Home")
            true
        }
        R.id.highscore -> {
            // Respond to navigation item 2 click
            true
        }
        R.id.history -> {
            // Respond to navigation item 2 click
            true
        }
        else -> false
    }
}

private lateinit var auth: FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigation.selectedItemId = R.id.home
        bottomNavigation.setOnNavigationItemSelectedListener(nav)



       val topAppBar =  findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            Log.e("TAG", "Drawer")


        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.more -> {
                    // Handle more item (inside overflow menu) press

                    Log.e("TAG", "more")

                    FirebaseAuth.getInstance().signOut()

                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        // User is still signed in
                        Log.e("TAG", "Sign out error")

                    } else {
                        // No user is signed in
                        val intent = Intent(this, LogInActivity::class.java).apply {
                        }
                        startActivity(intent)
                        finish()
                    }



                    true
                }
                else -> false
            }
        }

    }
}


