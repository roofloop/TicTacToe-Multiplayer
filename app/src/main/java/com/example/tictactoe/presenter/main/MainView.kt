package com.example.tictactoe.presenter.main

import android.app.Activity
import android.widget.ImageView

interface MainView {

    fun getActivity(): Activity

    fun onAcceptedRequest(opponentEmail: String)

    fun onGameListening(opponentEmail: String)

    fun onGameFinished()

    fun setTurnVisually(player: Int)

    fun getGameButton(id: Int): ImageView

    fun resetGame()

    fun resetBoard()
}