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




