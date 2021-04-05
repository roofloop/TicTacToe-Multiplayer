package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tictactoe.databinding.ActivityPlaySoloBinding
import java.util.*
import kotlin.collections.ArrayList

private lateinit var binding: ActivityPlaySoloBinding

class PlaySoloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaySoloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val board = arrayListOf("", "", "", "", "", "", "", "", "")

        binding.button0.setOnClickListener {
            if (board[0] == "") {
                binding.button0.text = "X"
                board[0] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button1.setOnClickListener {
            if (board[1] == "") {
                binding.button1.text = "X"
                board[1] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button2.setOnClickListener {
            if (board[2] == "") {
                binding.button2.text = "X"
                board[2] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding. button3.setOnClickListener {
            if (board[3] == "") {
                binding.button3.text = "X"
                board[3] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button4.setOnClickListener {
            if (board[4] == "") {
                binding.button4.text = "X"
                board[4] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button5.setOnClickListener {
            if (board[5] == "") {
                binding.button5.text = "X"
                board[5] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button6.setOnClickListener {
            if (board[6] == "") {
                binding.button6.text = "X"
                board[6] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button7.setOnClickListener {
            if (board[7] == "") {
                binding.button7.text = "X"
                board[7] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.button8.setOnClickListener {
            if (board[8] == "") {
                binding.button8.text = "X"
                board[8] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.buttonReset.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun getComputerMove(board: ArrayList<String>): Int {
        //check if computer can win in this move
        for (i in 0..board.count()-1){
            var copy: ArrayList<String> = getBoardCopy(board)
            if(copy[i] == "") copy[i] = "O"
            if(result(copy, "O")) return i
        }

        //check if player could win in the next move
        for (i in 0..board.count()-1){
            var copy2 = getBoardCopy(board)
            if(copy2[i] == "") copy2[i] = "X"
            if(result(copy2, "X")) return i
        }

        //try to take corners if its free
        var move = choseRandomMove(board, arrayListOf<Int>(0, 2, 6, 8))
        if(move != -1)
            return move

        //try to take center if its free
        if(board[4] == "") return 4

        //move on one of the sides
        return choseRandomMove(board, arrayListOf<Int>(1, 3, 5, 7))
    }


    private fun choseRandomMove(board: ArrayList<String>, list: ArrayList<Int>): Int {
        var possibleMoves = arrayListOf<Int>()
        for (i in list){
            if(board[i] == "") possibleMoves.add(i)
        }
        if(possibleMoves.isEmpty()) return -1
        else {
            var index = Random().nextInt(possibleMoves.count())
            return possibleMoves[index]
        }
    }

    private fun getBoardCopy(board: ArrayList<String>): ArrayList<String> {
        var bd = arrayListOf<String>("", "", "", "", "", "", "", "", "")
        for (i in 0..board.count()-1) {
            bd[i] = board[i]
        }
        return bd
    }

    private fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board)
            if(i != "X" && i != "O") return false
        return true
    }


    private fun resultOut(board: ArrayList<String>){


            if(result(board, "X")){
                startActivity(Intent(this, MainActivity::class.java))

            }else if(result(board, "O")){
                startActivity(Intent(this, MainActivity::class.java))

            }
            if(isBoardFull(board)){
                startActivity(Intent(this, MainActivity::class.java))

            }



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


    private fun displayComputerButton(position: Int){

        Handler(Looper.getMainLooper()).postDelayed({
            if(position == 0) binding.button0.text = "O"
            else if(position == 1) binding.button1.text = "O"
            else if(position == 2) binding.button2.text = "O"
            else if(position == 3) binding.button3.text = "O"
            else if(position == 4) binding.button4.text = "O"
            else if(position == 5) binding.button5.text = "O"
            else if(position == 6) binding.button6.text = "O"
            else if(position == 7) binding.button7.text = "O"
            else if(position == 8) binding.button8.text = "O"
        }, 500)

    }


    // for handling back buttton of the Android Device
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

}
