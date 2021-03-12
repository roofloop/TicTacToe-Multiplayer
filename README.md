# TicTacToe-Multiplayer

### Development information
Strongly influenced material choices with google material design as source. Buttons, textinputs, and navigation bars are all used from Google Material Desgin.

### App Demo - Create user and sign-in 
<img src="https://github.com/roofloop/TicTacToe-Multiplayer/blob/master/gif/TicTacToe_LogIn.gif" alt="screenshot" width="350"/>


### Bottom navigation bar - Google Material Design

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



### Top Application Bar - Google Material Design

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



