package com.example.tictactoe.presenter.main

import android.view.View
import androidx.lifecycle.LiveData

interface MainPresenterInterface {

    fun startFullListening()

    fun onResume()

    fun onStop()

    fun sendRequest(opponentEmail: String)

    fun handleAppbar()

    fun getHighScore(): LiveData<String>

    fun acceptRequest(opponentEmail: String)

    fun openModal()

    fun setEmail(email: String?)

    fun startEventListeners()

    fun sendMove(id: Int)

    fun resetGame()

    fun isPlaying(): Boolean




}