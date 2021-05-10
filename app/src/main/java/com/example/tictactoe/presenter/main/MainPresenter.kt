package com.example.tictactoe.presenter.main

import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.tictactoe.R
import com.example.tictactoe.activity.MainActivity
import com.example.tictactoe.data.FirebaseClient
import com.example.tictactoe.data.FirebaseListener
import com.example.tictactoe.data.PreferencesClient
import com.example.tictactoe.manager.GameLogic
import com.example.tictactoe.manager.NavigationManager
import com.example.tictactoe.manager.NotificationsManager
import com.example.tictactoe.model.Winner
import com.example.tictactoe.utils.Constants
import com.example.tictactoe.utils.getUserFromEmailForFirebase
import java.util.HashMap

class MainPresenter(private val view: MainView) : MainPresenterInterface, FirebaseListener {

    companion object {
        private const val TAG: String = "MainPresenter"
    }

    private val preferences by lazy { PreferencesClient(view.getActivity()) }
    private val firebaseDatabase by lazy { FirebaseClient(view.getActivity()).setListener(this) }
    private val notificationsManager by lazy { NotificationsManager(view.getActivity()) }
    private val gameLogic by lazy { GameLogic() }

    override fun onResume() {
        if (!isPlaying()) return
        view.setTurnVisually(if (gameLogic.canPlay(preferences.getPlayerNumber())) Constants.FIRST_PLAYER else Constants.SECOND_PLAYER)
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
        listenGame()
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

    override fun onFoundGame(opponentEmail: String) {
        view.onAcceptedRequest(opponentEmail)
        initGame(Constants.FIRST_PLAYER, opponentEmail)
2    }

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





    private fun listenGame() {
        if (!isPlaying()) return
        view.onGameListening(preferences.getOpponentEmail())
        listen()
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
        view.setTurnVisually(player)
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
                view.onGameFinished()
            }
            else -> {
            }
        }
    }


}