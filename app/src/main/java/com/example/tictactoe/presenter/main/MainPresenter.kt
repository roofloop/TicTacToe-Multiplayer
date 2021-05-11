package com.example.tictactoe.presenter.main

import android.graphics.Color
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.tictactoe.R
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.example.tictactoe.activity.LogInActivity
import com.example.tictactoe.data.FirebaseClient
import com.example.tictactoe.data.FirebaseListener
import com.example.tictactoe.data.PreferencesClient
import com.example.tictactoe.manager.GameLogic
import com.example.tictactoe.manager.NavigationManager
import com.example.tictactoe.manager.NotificationsManager
import com.example.tictactoe.model.Winner
import com.example.tictactoe.utils.Constants
import com.example.tictactoe.utils.getUserFromEmailForFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainPresenter(private val view: MainView) : MainPresenterInterface, FirebaseListener {

    companion object {
        private const val TAG: String = "MainPresenter"
    }

    private val preferences by lazy { PreferencesClient(view.getActivity()) }
    private val firebaseDatabase by lazy { FirebaseClient(view.getActivity()).setListener(this) }
    private val notificationsManager by lazy { NotificationsManager(view.getActivity()) }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val gameLogic by lazy { GameLogic() }

    override fun onResume() {
        if (!isPlaying()) return
    }

    override fun onStop() {
        if (isPlaying() && gameLogic.gameFinished && preferences.getPlayerNumber() == Constants.SECOND_PLAYER) {
            gameLogic.gameFinished = false
            resetGame()
        }
        firebaseDatabase.remove()
    }

    override fun setEmail(email: String?) {
        preferences.setEmail(email)
    }

    override fun startFullListening() {
        listenIncomingRequests()
        listenAcceptedRequests()
    }

    override fun startEventListeners() {
        listenIncomingRequests()
        listenAcceptedRequests()
    }

    override fun onIncomingRequests(opponentEmail: String) {
        if (!isPlaying()) {
            notificationsManager.showNotification(opponentEmail)
            firebaseDatabase.clearIncomingRequests()
        }
    }

    override fun onAcceptedRequests(opponentEmail: String) {
        view.onAcceptedRequest(opponentEmail)
        initGame(Constants.FIRST_PLAYER, opponentEmail)
        firebaseDatabase.clearAcceptedRequests()
    }

    override fun onGameEvent(moves: HashMap<*, *>) {
        gameLogic.clearButtons()
        view.resetBoard()
        var email: String
        for (key in moves.keys) {
            email = moves[key] as String
            val player: Int
            if (email == preferences.getEmail()) {
                player = if (preferences.getPlayerNumber() == Constants.FIRST_PLAYER) Constants.FIRST_PLAYER else Constants.SECOND_PLAYER
            } else {
                player = if (preferences.getPlayerNumber() == Constants.FIRST_PLAYER) Constants.SECOND_PLAYER else Constants.FIRST_PLAYER
            }
            gameLogic.currentPlayer = player
            play((key as String).replace(Constants.CEL_ID__PREFIX, "").toInt())
        }
    }

    override fun openModal(){

        val flatDialog = FlatDialog(view.getActivity())
        flatDialog.setTitle("Invite friend to play Tic-Tac-Toe")
            .setTitleColor(Color.parseColor("#000000"))
            .setBackgroundColor(Color.parseColor("#FFFFFF"))
            .setFirstTextFieldHint("Write email address here...")
            .setFirstTextFieldInputType(TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            .setFirstTextFieldHintColor(Color.parseColor("#000000"))
            .setFirstTextFieldBorderColor(Color.parseColor("#000000"))
            .setFirstTextFieldTextColor(Color.parseColor("#000000"))
            .setFirstButtonColor(Color.parseColor("#FF6200EE"))
            .setSecondButtonColor(Color.parseColor("#FF6200EE"))
            .setFirstButtonTextColor(Color.parseColor("#FFFFFF"))
            .setSecondButtonTextColor(Color.parseColor("#FFFFFF"))
            .setFirstButtonText("Done")
            .setSecondButtonText("Cancel")
            .withFirstButtonListner {

                val textInField = flatDialog.firstTextField.toString()
                sendRequest(textInField)
                Log.i(TAG, "textInField : $textInField")

                flatDialog.dismiss()

            }
            .withSecondButtonListner {
                flatDialog.dismiss()
            }
            .show()
    }

    override fun handleAppbar(){

        view.getActivity().topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    Log.e("TAG", "more")
                    firebaseDatabase.signOut()
                    loadMainActivity()

                    true
                }
                else -> false
            }
        }
    }

    override fun sendRequest(opponentEmail: String) {

        Log.i(TAG, "sendRequest function preferences string is: " + preferences.getEmail() )
        Log.i(TAG, "sendRequest function opponentemail string is: $opponentEmail")

        if (preferences.getEmail().isNullOrBlank() || opponentEmail.isNullOrBlank() || preferences.getEmail() == opponentEmail) return
        firebaseDatabase.sendRequest(opponentEmail)

    }

    override fun acceptRequest(opponentEmail: String) {
        if (isPlaying() || preferences.getEmail().isNullOrBlank() || preferences.getEmail() == opponentEmail) return
        firebaseDatabase.acceptRequest(opponentEmail)
        initGame(Constants.SECOND_PLAYER, opponentEmail)
    }

    override fun sendMove(id: Int) {
        if (!isPlaying() || !gameLogic.canPlay(preferences.getPlayerNumber())) return
        firebaseDatabase.sendMove(Constants.CEL_ID__PREFIX + id)
    }

    override fun isPlaying(): Boolean {
        return preferences.isPlaying()
    }

    override fun resetGame() {
        Log.i(TAG, "Game RESET")
        gameLogic.resetGame()
        view.resetGame()
        if (preferences.getPlayerNumber() == Constants.FIRST_PLAYER) {
            firebaseDatabase.resetGame()
        }
        preferences.resetGame()
    }

    private fun listenIncomingRequests() {
        firebaseDatabase.listenIncomingRequests()
    }

    private fun listenAcceptedRequests() {
        firebaseDatabase.listenAcceptedRequests()
    }

    private fun initGame(player: Int, opponentEmail: String) {
        val sessionId: String
        preferences.setPlayerNumber(player)
        preferences.setIsPlaying(true)
        preferences.setOpponentEmail(opponentEmail)
        when (player) {
            Constants.FIRST_PLAYER -> sessionId = getUserFromEmailForFirebase(preferences.getEmail()) + "_" + getUserFromEmailForFirebase(opponentEmail)
            Constants.SECOND_PLAYER -> sessionId = getUserFromEmailForFirebase(opponentEmail) + "_" + getUserFromEmailForFirebase(preferences.getEmail())
            else -> {
                sessionId = ""
            }
        }
        preferences.setSessionId(sessionId)
        listen()
    }

    private fun listen() {
        if (!isPlaying()) return
        firebaseDatabase.listenGame()
    }

    private fun play(id: Int) {
        val gameButton: ImageView?
        gameButton = view.getGameButton(id)
        val drawable: Int = gameLogic.play(id)
        gameButton.isEnabled = false
        gameButton.setImageDrawable(ContextCompat.getDrawable(view.getActivity(), drawable))
        onResume()
        val winner: Winner? = gameLogic.checkWinner() ?: return
        when (winner?.player) {
            Constants.FIRST_PLAYER -> {
                view.onGameFinished()
                winner.winnerValues.forEach { it ->
                    view.getGameButton(it).setImageDrawable(ContextCompat.getDrawable(view.getActivity(), R.drawable.cell_x_win))
                }
            }
            Constants.SECOND_PLAYER -> {
                view.onGameFinished()
                winner.winnerValues.forEach { it ->
                    view.getGameButton(it).setImageDrawable(ContextCompat.getDrawable(view.getActivity(), R.drawable.cell_o_win))
                }
            }
            Constants.DRAW -> {
                firebaseDatabase.incrementDraws()
                view.onGameFinished()

            }
            else -> {
            }
        }
    }


    private fun loadMainActivity() {
        NavigationManager()
                .finishingCurrent()
                .goTo(view.getActivity(), LogInActivity::class.java)
    }
}