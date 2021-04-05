package com.example.tictactoe.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tictactoe.R
import com.example.tictactoe.model.Firestore
import com.example.tictactoe.model.FirestoreModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import com.google.firebase.firestore.ListenerRegistration


private val db = Firebase.firestore
private var activeGame: String? = ""
private lateinit var firestoreHelper:FirestoreModel
private lateinit var auth: FirebaseAuth
private lateinit var feedback: ListenerRegistration


class PlayOnlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_online)

        firestoreHelper = FirestoreModel()
        auth = FirebaseAuth.getInstance()

        createNewOnlineGame()
    }

    private fun createNewOnlineGame() {
        //Create a new game
        val task = Firestore()
        val currentDate = getCurrentDate()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user.uid
        val board = arrayListOf("", "", "", "", "", "", "", "", "")


        val list = mutableListOf<Firestore>()
        task.creationDate = currentDate
        task.player1 = uid
        task.searching = true
        task.board = board

        list.add(task)

        firestoreHelper.addToFirestore(task, uid)
        waitForUserToJoin(applicationContext)

    }

    override fun onDestroy() {
        super.onDestroy()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user.uid
       // firestoreHelper.deleteFromFirestore(uid)
        feedback.remove()
        finish()
    }
}

private fun waitForUserToJoin(context: Context) {
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user.uid
    val docRef = db.collection("Games").document(uid)

     feedback = docRef.addSnapshotListener {snapshot, e ->
        if (e != null) {
            Log.w("TAG", "Listen failed.", e)
            return@addSnapshotListener

        }

        if (snapshot != null && snapshot.exists()) {

            if(snapshot.get("searching") == false){

                Log.d("TAG", "searching switched to false and a player has joined")

                val intent = Intent(context, OnlineGameActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id", user.uid)
                context.startActivity(intent)
                feedback.remove()



            }


        } else {
            Log.d("TAG", "Current data: null")
        }

    }
}


private fun getCurrentDate() : String{
    val sdf = SimpleDateFormat("yyyy/M/dd hh:mm:ss")
    return sdf.format(Date())
}





