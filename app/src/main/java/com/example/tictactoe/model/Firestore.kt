package com.example.tictactoe.model
import java.io.Serializable

class Firestore : Serializable{

    var creationDate: String? = null
    var finished: Boolean? = null
    var searching: Boolean? = null
    var isMyTurn: String? = null
    var board: ArrayList<String>? = null
    var player1: String? = null
    var player2: String? = null

    constructor() {}

    constructor(creationDate: String, finished: Boolean, searching: Boolean,isMyTurn: String,board: ArrayList<String>, player1: String, player2: String) {
        this.creationDate = creationDate
        this.finished = finished
        this.searching = searching
        this.isMyTurn = isMyTurn
        this.board = board
        this.player1 = player1
        this.player2 = player2

    }
}


