package com.example.tictactoe.presenter.main

import android.app.Activity
import android.view.View
import android.widget.ImageView

interface MainView {

    fun getActivity(): Activity

    fun onAcceptedRequest(opponentEmail: String)

    fun onGameFinished()

    fun getGameButton(id: Int): ImageView

    fun resetGame()

    fun resetBoard()

}