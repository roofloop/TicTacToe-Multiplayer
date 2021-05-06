package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.model.FirestoreModel

open class BaseActivity : AppCompatActivity() {



    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}