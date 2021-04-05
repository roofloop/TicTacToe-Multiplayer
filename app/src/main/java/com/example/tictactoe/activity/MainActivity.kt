package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.tictactoe.R
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

        val playSoloButton = findViewById<Button>(R.id.play_solo_button)
        val playOnlineButton = findViewById<Button>(R.id.play_online_button)
        val JoinGameButton = findViewById<Button>(R.id.join_game_button)

        val user = FirebaseAuth.getInstance().currentUser





        val topAppBar =  findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            Log.e("TAG", "Drawer")


        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    FirebaseAuth.getInstance().signOut()

                    if (user != null) {
                        // User is still signed in
                        Log.e("TAG", "Sign out error" + user.isAnonymous)
                        Log.e("TAG", "Sign out error$user.")

                    } else {
                        // No user is signed in
                        startActivity(Intent(this, LogInActivity::class.java))
                        finish()
                    }



                    true
                }
                else -> false
            }
        }

        playSoloButton.setOnClickListener {

            startActivity(Intent(this, PlaySoloActivity::class.java))

        }

        playOnlineButton.setOnClickListener {

            startActivity(Intent(this, PlayOnlineActivity::class.java))

        }

        JoinGameButton.setOnClickListener {
            startActivity(Intent(this, JoinGameActivity::class.java))

        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }
}


