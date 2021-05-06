package com.example.tictactoe.activity

import android.os.Bundle
import android.util.Log
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityOnlineGameBinding
import com.example.tictactoe.model.Firestore
import com.example.tictactoe.model.FirestoreModel
import com.example.tictactoe.utils.isBoardFull
import com.example.tictactoe.utils.result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var firestoreHelper: FirestoreModel
private lateinit var binding: ActivityOnlineGameBinding
private val db = Firebase.firestore
private lateinit var board : ArrayList<String>
private lateinit var player1Id : String
private lateinit var player2Id : String
private lateinit var creatorId : String
private lateinit var turnToPlay : String
private lateinit var xPieceFromFirestore : String
private lateinit var myPiece : String
private lateinit var uid : String
private lateinit var feedback: ListenerRegistration


class OnlineGameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game)
        firestoreHelper = FirestoreModel()
        val user = FirebaseAuth.getInstance().currentUser
        uid = user.uid

        binding = ActivityOnlineGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        creatorId = intent.getStringExtra("creatorId").toString()
        val topAppBar =  findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        getGameInfo()
    }

}

private fun getGameInfo() {
    val docRef = db.collection("Games").document(creatorId)
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user.uid

    docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    player1Id = document.get("player1") as String
                    player2Id = document.get("player2") as String
                    xPieceFromFirestore = document.get("xpiece") as String

                    myPiece = if(xPieceFromFirestore == uid){"X"}else{"O"}
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

    feedback = docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("TAG", "Listen failed.", e)
            return@addSnapshotListener
        }
        if (snapshot != null && snapshot.exists()) {
            board = snapshot.get("board") as ArrayList<String>
            updateBoard()

            try{

                turnToPlay = snapshot.get("turnToPlay") as String

                Log.d("TAG", "turntoPlay is: $turnToPlay : my uid Is: $uid")

                if (turnToPlay == uid) {
                    binding.title.text = "Your turn to play"
                    disableBoard()
                    buttonsToPlay(myPiece)
                } else {
                    disableBoard()
                    binding.title.text = "Waiting for your turn"
                }
                getResult()
            }catch(error: Error){Log.d("TAG", "Error: $error")}


        } else {
            Log.d("TAG", "Current data: null")
        }
    }
}


private fun getResult() {

    if (result(board, "X")) {
        //startActivity(Intent(this, MainActivity::class.java))
        binding.title.text = "The winner is X"
        disableBoard()
        val washingtonRef = db.collection("Games").document(uid)

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("finished", true,"searching", true)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
        feedback.remove()

    } else if (result(board, "O")) {
        //startActivity(Intent(this, MainActivity::class.java))
        binding.title.text = "The winner is O"
        disableBoard()
        val washingtonRef = db.collection("Games").document(uid)

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("finished", true,"searching", true)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
        feedback.remove()
    }
    if (isBoardFull(board)) {
        //startActivity(Intent(this, MainActivity::class.java))
        binding.title.text = "It's a tie!"
        disableBoard()
        val washingtonRef = db.collection("Games").document(uid)

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("finished", true,"searching", true)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
        feedback.remove()
    }
}

private fun buttonsToPlay(myPiece: String) {
    if(board[0] != ""){
        binding.button0.isClickable = false
    }else{
        binding.button0.setOnClickListener {
            if (board[0] == "") {
                Log.d("TAG", "BOARD 0 = " + board[0])
                binding.button0.text = myPiece //My piece
                board[0] = myPiece
            }

            if(turnToPlay == player1Id){
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)

            }else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }

    if(board[1] != ""){
        binding.button1.isClickable = false
    }else {
        binding.button1.setOnClickListener {
            if (board[1] == "") {
                binding.button1.text = myPiece
                board[1] = myPiece
            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)

            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }

    if(board[2] != ""){
        binding.button2.isClickable = false
    }else {
        binding.button2.setOnClickListener {
            if (board[2] == "") {
                binding.button2.text = myPiece
                board[2] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }
    if(board[3] != ""){
        binding.button3.isClickable = false
    }else {
        binding.button3.setOnClickListener {
            if (board[3] == "") {
                binding.button3.text = myPiece
                board[3] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }

    if(board[4] != ""){
        binding.button4.isClickable = false
    }else {
        binding.button4.setOnClickListener {
            if (board[4] == "") {
                binding.button4.text = myPiece
                board[4] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }
    if(board[5] != ""){
        binding.button5.isClickable = false
    }else {
        binding.button5.setOnClickListener {
            if (board[5] == "") {
                binding.button5.text = myPiece
                board[5] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }

    if(board[6] != ""){
        binding.button6.isClickable = false
    }else {
        binding.button6.setOnClickListener {
            if (board[6] == "") {
                binding.button6.text = myPiece
                board[6] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }
    if(board[7] != ""){
        binding.button7.isClickable = false
    }else {
        binding.button7.setOnClickListener {
            if (board[7] == "") {
                binding.button7.text = myPiece
                board[7] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }

    if(board[8] != ""){
        binding.button8.isClickable = false
    }else {
        binding.button8.setOnClickListener {
            if (board[8] == "") {
                binding.button8.text = myPiece
                board[8] = myPiece

            }
            if (turnToPlay == player1Id) {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player2Id)
            } else {
                firestoreHelper.updateBoardInFirestore(player1Id, board, player1Id)
            }
        }
    }
}


private fun disableBoard() {
    binding.button0.isClickable = false
    binding.button1.isClickable = false
    binding.button2.isClickable = false
    binding.button3.isClickable = false
    binding.button4.isClickable = false
    binding.button5.isClickable = false
    binding.button6.isClickable = false
    binding.button7.isClickable = false
    binding.button8.isClickable = false

}

private fun updateBoard() {
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
