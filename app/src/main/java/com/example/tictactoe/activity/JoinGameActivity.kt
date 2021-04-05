package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tictactoe.R
import com.example.tictactoe.adapter.RecyclerViewAdapter
import com.example.tictactoe.model.Firestore
import com.example.tictactoe.model.FirestoreModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join_game.*

private val db = Firebase.firestore
private lateinit var mFirestoreAdapter: RecyclerViewAdapter

class JoinGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_game)
    }

    override fun onStart() {
        super.onStart()
        searchGames()

    }
    private fun searchGames() {
        val docRef = db.collection("Games")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                for (doc in snapshot.documents) {
                    Log.d("TAG", "Current data $doc")
                    getFirestoreToRV()

                }
            } else {
                Log.d("TAG", "Current data: null")
            }
        }

    }


    private fun getFirestoreToRV() {
        val firestoreModel = FirestoreModel()
        firestoreModel.getFromFirestore(applicationContext) { list ->
            populateTheRecyclerView(list)
        }
    }


    private fun populateTheRecyclerView(notesList: MutableList<Firestore>) {
        //val sortedList = SortingFunctions.dateInsertionSorting(notesList)
        mFirestoreAdapter = RecyclerViewAdapter(notesList)
        postRV.adapter = mFirestoreAdapter
        postRV.layoutManager = StaggeredGridLayoutManager(
            1,
            LinearLayoutManager.VERTICAL
        )

        mFirestoreAdapter.onItemClick = { firestoreVariables ->
            Log.w("TAG", "Click " + firestoreVariables.player1)

            val updateDiaryInputRef = db.collection("Games").document(firestoreVariables.player1!!)

            val user = FirebaseAuth.getInstance().currentUser
            val uid = user.uid

            updateDiaryInputRef
                .update("searching", false, "player2", uid, "isMyTurn", uid)
                .addOnSuccessListener {

                    Log.d("TAG", "DocumentSnapshot successfully updated!")
                    val intent = Intent(applicationContext, OnlineGameActivity::class.java)
                    intent.putExtra("id", firestoreVariables.player1)
                    intent.putExtra("opponentId", uid)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error updating document", e)


                }
        }
    }
}
