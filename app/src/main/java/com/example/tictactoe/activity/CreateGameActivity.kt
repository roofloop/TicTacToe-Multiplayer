package com.example.tictactoe.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R
import com.example.tictactoe.model.Firestore
import com.example.tictactoe.model.FirestoreModel
import com.example.tictactoe.utils.MatchmakingUtility
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

private lateinit var firestoreHelper:FirestoreModel
private lateinit var auth: FirebaseAuth
private lateinit var uid: String

class PlayOnlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_online)
        firestoreHelper = FirestoreModel()
        auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        uid = user.uid

        setUpMatchmakingInfo()
        handleSearchingGame()

    }


    private fun setUpMatchmakingInfo() {
        //Create a new game
        val task = Firestore()
        val currentDate = getCurrentDate()
        val list = mutableListOf<Firestore>()

        val board = arrayListOf("", "", "", "", "", "", "", "", "")
        val randomNumber: Int = Random().nextInt(2)

        if (randomNumber == 0) {
            task.xPiece = uid
            task.oPiece = ""
        } else {
            task.oPiece = uid
            task.xPiece = ""
        }

        task.creationDate = currentDate
        task.finished = false
        task.player1 = uid
        task.player2 = ""
        task.searching = true
        task.board = board
        task.turnToPlay = uid

        list.add(task)

        firestoreHelper.addMatchmakingInfoToFirestore(task, uid)

    }

    private fun handleSearchingGame() {
        MatchmakingUtility.waitForUserToJoin().observe(this, { opponentSearch ->

            Log.w("TAG", "handleSearching opponentSearch = $opponentSearch")

            // If there is an opponent that has joined the game
            if (!opponentSearch) {
                val intent = Intent(this, OnlineGameActivity::class.java)
                intent.putExtra("creatorId", uid)
                startActivity(intent)
                finish()
            } else {
                Log.w("TAG", "handleSearching opponentSearch = $opponentSearch")
            }
        })
    }
}


private fun removeCurrentGame() {

    firestoreHelper.deleteFromFirestore(uid)

}

private fun getCurrentDate() : String{
    val sdf = SimpleDateFormat("yyyy/M/dd hh:mm:ss")
    return sdf.format(Date())
}





