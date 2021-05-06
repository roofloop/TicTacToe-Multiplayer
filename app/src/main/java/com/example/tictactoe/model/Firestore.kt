package com.example.tictactoe.model
import java.io.Serializable

class Firestore : Serializable{

    var creationDate: String? = null
    var finished: Boolean? = null
    var searching: Boolean? = null
    var turnToPlay: String? = null
    var board: ArrayList<String>? = null
    var player1: String? = null
    var player2: String? = null
    var xPiece: String? = null
    var oPiece: String? = null



    constructor() {}

    constructor(creationDate: String, finished: Boolean, searching: Boolean,turnToPlay: String,board: ArrayList<String>, player1: String, player2: String, xPiece: String, oPiece: String) {
        this.creationDate = creationDate
        this.finished = finished
        this.searching = searching
        this.turnToPlay = turnToPlay
        this.board = board
        this.player1 = player1
        this.player2 = player2
        this.xPiece = xPiece
        this.oPiece = oPiece

    }
}


