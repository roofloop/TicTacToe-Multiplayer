package com.example.tictactoe.presenter.main

interface MainPresenterInterface {

    fun startFullListening()

    fun onResume()

    fun onStop()

    fun sendRequest(opponentEmail: String)



    fun setEmail(email: String?)

    fun startEventListeners()

    fun sendMove(id: Int)

    fun resetGame()


    fun isPlaying(): Boolean

    fun acceptRequest(opponentEmail: String)

}