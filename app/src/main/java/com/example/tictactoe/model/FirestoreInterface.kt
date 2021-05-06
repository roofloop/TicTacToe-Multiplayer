package com.example.tictactoe.model

import android.content.Context

interface FirestoreInterface {

    fun addMatchmakingInfoToFirestore(firestore: Firestore, id: String)
    fun getFromFirestore(context: Context, callback: (MutableList<Firestore>) -> Unit)
    fun deleteFromFirestore(id: String)
    fun updateBoardInFirestore(id: String, list: ArrayList<String>, idToPlay: String)
    fun updateConnectedState(id: String)

}