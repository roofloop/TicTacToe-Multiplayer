# TicTacToe-Multiplayer

### Development information
Strongly influenced material choices with google material design as source. Buttons, textinputs, and navigation bars are all used from Google Material Desgin.

### App Demo - Sign in page 
<img src="https://github.com/roofloop/TicTacToe-Multiplayer/blob/master/demonstration/signin.png" alt="screenshot" width="350"/>


#### Sign in code

    LoginActivity.kt
    
    private val presenter: LoginPresenter by lazy { LoginPresenter(this) }
    
    //When pressing the button to log in
    fun loginButtonClick(view: View) {
        when (view.id) {
            R.id.login_button -> presenter.signInButtonClick(view)
            else -> {
            }
        }
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }
    
    // Try sign in or throw exception
    private fun signInIntoFirebase(view: View) {
        Log.i(TAG, "Trying to Sign in into Firebase" + this.view.getEmail())

        try {
            firebaseAuth.signInWithEmailAndPassword(this.view.getEmail(), this.view.getPassword())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            loadMainActivity()
                        } else {

                            if (it.exception != null) {
                                Snackbar.make(view, it.exception?.message ?: view.context.getString(R.string.firebase_sign_in_fail), Snackbar.LENGTH_SHORT).show()
                                Log.e(TAG, it.exception?.message, it.exception)
                            }
                        }
                    }
        }catch(e: Exception){Log.d(TAG, "Error signing in")}

    }




### App Demo - Invite friend 
<img src="https://github.com/roofloop/TicTacToe-Multiplayer/blob/master/demonstration/inviteFriend.png" alt="screenshot" width="350"/>

#### Invite friend to game code

    MainActivity.kt
    
    // When pressing the play vs friend button - function openModal() is called
    fun buttonClick(view: View) {
        when (view.id) {
            R.id.resetGameButton -> { presenter.resetGame() }
            R.id.play_vs_friend_button -> { presenter.openModal() }
            R.id.play_solo_button -> {startActivity(Intent(this, PlaySoloActivity::class.java))}

            else -> {

            }
        }
    }
    
    //The function openModal() gets the input email and calls sendRequest(email)
    
    fun sendRequest(opponentEmail: String?) {
        if (isPlaying()) return
        userReference(opponentEmail)
            .child(Constants.INCOMING_REQUEST_FROM_KEY)
            .push()
            .setValue(preferences.getEmail())
    }
    
    
### App Demo - Gameplay
<img src="https://github.com/roofloop/TicTacToe-Multiplayer/blob/master/demonstration/gameVsFriend.png" alt="screenshot" width="350"/>    

### Gameplay
    
    GameLogic.kt
    
    //This class handles gamelogic when playing vs friend
    
     /**
     * Manages the moves. It returns the drawable to be displayed for the move depending on the current player.
     */
    fun play(buttonId: Int): Int {
        when (currentPlayer) {
            Constants.FIRST_PLAYER -> {
                player1Buttons.add(buttonId)
                currentPlayer = Constants.SECOND_PLAYER
                return R.drawable.cell_x
            }
            Constants.SECOND_PLAYER -> {
                player2Buttons.add(buttonId)
                currentPlayer = Constants.FIRST_PLAYER
                return R.drawable.cell_o
            }
            else -> return 0
        }
    }
