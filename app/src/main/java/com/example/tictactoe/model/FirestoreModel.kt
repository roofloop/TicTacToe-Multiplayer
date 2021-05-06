package com.example.tictactoe.model

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.tictactoe.activity.OnlineGameActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreModel : FirestoreInterface {

    private val db = Firebase.firestore

    override fun addMatchmakingInfoToFirestore(firestore: Firestore, id: String) {
        try {
            val newGameInFirestore = db.collection("Games").document(id)
            firestore.player1 = newGameInFirestore.id

            newGameInFirestore
                    .set(firestore)
                    .addOnSuccessListener {

                        Log.d("TAG", "DocumentSnapshot added with ID:" + newGameInFirestore.id)

                    }
                    .addOnFailureListener { e ->

                        Log.w("TAG", "Error adding document", e)
                    }

        } catch (e: Exception) {
            Log.w("TAG", "Error adding to firestore", e)
        }
    }

    override fun getFromFirestore(context: Context, callback: (MutableList<Firestore>) -> Unit) {

        val diaryInputsList = mutableListOf<Firestore>()

        try {
            db.collection("Games")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        diaryInputsList.clear()

                        if (snapshot != null && !snapshot.isEmpty) {
                            for (doc in snapshot.documents) {
                                val diaryInputs = doc.toObject(Firestore::class.java)

                                // Adding data from firestore to out mutableList
                                diaryInputsList.add(diaryInputs!!)
                            }
                            // Returning the up to date mutableList
                            callback(diaryInputsList)

                        } else {
                            //Refreshing the RV and deleting the cache if firestore is empty.
                            callback(diaryInputsList)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d("TAG", "Error getting documents: ", e)
                    }
        } catch (e: Exception) {
            Log.d("TAG", "Failure", e)
        }
    }

    override fun deleteFromFirestore(id: String) {
        db.collection("Games").document(id)
                .delete()
                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error deleting document", e)
                }
    }

    override fun updateBoardInFirestore(id: String, list: ArrayList<String>, idToPlay: String) {
        val updateDiaryInputRef = db.collection("Games").document(id)

        updateDiaryInputRef
                .update("board", list, "turnToPlay", idToPlay)
                .addOnSuccessListener {

                    Log.d("TAG", "Updated the board")

                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error updating document", e)
                }
    }

    override fun updateConnectedState(id: String) {
        val updateDiaryInputRef = db.collection("Games").document(id)

        updateDiaryInputRef
                .update("searching", false)
                .addOnSuccessListener {

                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error updating document", e)
                }
    }
}