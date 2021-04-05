package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityOnlineGameBinding
import com.example.tictactoe.model.FirestoreModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var firestoreHelper: FirestoreModel

private lateinit var binding: ActivityOnlineGameBinding
private val db = Firebase.firestore
private lateinit var board : ArrayList<String>





class OnlineGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game)
        firestoreHelper = FirestoreModel()

        binding = ActivityOnlineGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getGameInfo()

    }


    private fun buttonsToPlay() {
        val firestoreModel = FirestoreModel()

        binding.button0.setOnClickListener {
            if (board[0] == "") {
                binding.button0.text = "X"
                board[0] = "X"
            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button1.setOnClickListener {
            if (board[1] == "") {
                binding.button1.text = "X"
                board[1] = "X"
            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button2.setOnClickListener {
            if (board[2] == "") {
                binding.button2.text = "X"
                board[2] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding. button3.setOnClickListener {
            if (board[3] == "") {
                binding.button3.text = "X"
                board[3] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button4.setOnClickListener {
            if (board[4] == "") {
                binding.button4.text = "X"
                board[4] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button5.setOnClickListener {
            if (board[5] == "") {
                binding.button5.text = "X"
                board[5] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button6.setOnClickListener {
            if (board[6] == "") {
                binding.button6.text = "X"
                board[6] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button7.setOnClickListener {
            if (board[7] == "") {
                binding.button7.text = "X"
                board[7] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }

        binding.button8.setOnClickListener {
            if (board[8] == "") {
                binding.button8.text = "X"
                board[8] = "X"

            }
            firestoreModel.updateBoardInFirestore(idFromIntent, board)
            resultOut(board)
        }
    }

    private fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board)
            if(i != "X" && i != "O") return false
        return true
    }

    private fun result(bd: ArrayList<String>, s: String): Boolean =
        if(bd[0] == s && bd[1] == s && bd[2] == s) true
        else if(bd[3] == s && bd[4] == s && bd[5] == s) true
        else if(bd[6] == s && bd[7] == s && bd[8] == s) true
        else if(bd[0] == s && bd[3] == s && bd[6] == s) true
        else if(bd[1] == s && bd[4] == s && bd[7] == s) true
        else if(bd[2] == s && bd[5] == s && bd[8] == s) true
        else if(bd[0] == s && bd[4] == s && bd[8] == s) true
        else bd[2] == s && bd[4] == s && bd[6] == s


    private fun resultOut(board: ArrayList<String>){
        if(result(board, "X")){
            //startActivity(Intent(this, MainActivity::class.java))
        }else if(result(board, "O")){
            //startActivity(Intent(this, MainActivity::class.java))
        }
        if(isBoardFull(board)){
            //startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getGameInfo() {
        val docRef = db.collection("Games").document(idFromIntent)
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user.uid
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                board = snapshot.get("board") as ArrayList<String>

                updatedBoard()
                resultOut(board)

                if(snapshot.get("isMyTurn") == uid){
                    buttonsToPlay()
                }
            } else {
                Log.d("TAG", "Current data: null")
            }
        }

    }


    private fun updatedBoard() {
        binding.button0.text = board[0]
        binding.button1.text = board[1]
        binding.button2.text = board[2]
        binding.button3.text = board[3]
        binding.button4.text = board[4]
        binding.button5.text = board[5]
        binding.button6.text = board[6]
        binding.button7.text = board[7]
        binding.button8.text = board[8]
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user.uid
        firestoreHelper.deleteFromFirestore(uid)
        finish()
    }
}