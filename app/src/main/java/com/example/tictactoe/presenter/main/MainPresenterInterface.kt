package com.example.tictactoe.presenter.main

import android.view.View

interface MainPresenterInterface {

    fun startFullListening()

    fun onResume()

    fun onStop()

    fun sendRequest(opponentEmail: String)

    fun handleAppbar()

    fun acceptRequest(opponentEmail: String)

    fun openModal()

    fun setEmail(email: String?)

    fun startEventListeners()

    fun sendMove(id: Int)

    fun resetGame()

    fun isPlaying(): Boolean




}