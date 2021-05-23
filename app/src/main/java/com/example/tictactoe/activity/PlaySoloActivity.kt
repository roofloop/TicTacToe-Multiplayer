package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityPlaySoloBinding
import kotlinx.android.synthetic.main.activity_play_solo.*
import java.util.*
import kotlin.collections.ArrayList

private lateinit var binding: ActivityPlaySoloBinding


class PlaySoloActivity : AppCompatActivity() {

    private val count: Int by lazy { gameContainer.childCount }


    companion object {
        private const val TAG: String = "PlaySoloActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaySoloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val board = arrayListOf("", "", "", "", "", "", "", "", "")

        binding.gameButton1.setOnClickListener {
            if (board[0] == "") {
                binding.gameButton1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")
                board[0] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton2.setOnClickListener {
            if (board[1] == "") {
                binding.gameButton2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[1] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton3.setOnClickListener {
            if (board[2] == "") {
                binding.gameButton3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[2] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton4.setOnClickListener {
            if (board[3] == "") {
                binding.gameButton4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[3] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton5.setOnClickListener {
            if (board[4] == "") {
                binding.gameButton5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[4] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton6.setOnClickListener {
            if (board[5] == "") {
                binding.gameButton6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[5] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton7.setOnClickListener {
            if (board[6] == "") {
                binding.gameButton7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[6] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton8.setOnClickListener {
            if (board[7] == "") {
                binding.gameButton8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")
                board[7] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
        }

        binding.gameButton9.setOnClickListener {
            if (board[8] == "") {
                binding.gameButton9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_x))
                Log.d(TAG, "board: $board")

                board[8] = "X"
                if(!isBoardFull(board) && !result(board, "X")) {
                    val position = getComputerMove(board)
                    board[position] = "O"
                    displayComputerButton(position)
                }
            }
            resultOut(board)
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

    fun buttonClick(view: View) {
        when (view.id) {
            R.id.playAgainButton -> {startActivity(Intent(this, PlaySoloActivity::class.java))}

            else -> {

            }
        }
    }

    private fun resultOut(board: ArrayList<String>){

        Handler(Looper.getMainLooper()).postDelayed({

        if(result(board, "X")){
            enableGameButtons(false)
            binding.playAgainButton.visibility = View.VISIBLE

        }else if(result(board, "O")){
            enableGameButtons(false)
            binding.playAgainButton.visibility = View.VISIBLE


        }
        if(isBoardFull(board)){
            enableGameButtons(false)
            binding.playAgainButton.visibility = View.VISIBLE
        }
        } , 500)
    }

    private fun enableGameButtons(value: Boolean) {
        (0 until count)
            .filter { gameContainer.getChildAt(it) is ImageView }
            .forEach { gameContainer.getChildAt(it).isEnabled = value }
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
            when (position) {
                0 -> binding.gameButton1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                1 -> binding.gameButton2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                2 -> binding.gameButton3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                3 -> binding.gameButton4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                4 -> binding.gameButton5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                5 -> binding.gameButton6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                6 -> binding.gameButton7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                7 -> binding.gameButton8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
                8 -> binding.gameButton9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cell_o))
            }
        }, 500)
    }
    
    // for handling back buttton of the Android Device
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

}
