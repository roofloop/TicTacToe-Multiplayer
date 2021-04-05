package com.example.tictactoe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.tictactoe.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val userEmail = findViewById<TextInputEditText>(R.id.email_edit_text)
        val userPass = findViewById<TextInputEditText>(R.id.password_edit_text)
        val logInButton = findViewById<Button>(R.id.login_button)
        val anonymousLogInButton = findViewById<Button>(R.id.anonymous_button)

        // Initialize Firebase Auth and see if user already signed in
        auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // User is signed in
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
            finish()
        }

        logInButton.setOnClickListener {
            if (userEmail.text.toString() == "" || userPass.text.toString() == "") {
                Toast.makeText(
                    baseContext, "Please type something",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                // Sign in with the typed in credentials
                auth.signInWithEmailAndPassword(userEmail.text.toString(), userPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java).apply {}
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed. Wrong credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        anonymousLogInButton.setOnClickListener {
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            // Do something in response to button
                            val intent = Intent(this, MainActivity::class.java).apply {
                            }
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInAnonymously:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }


    }
    fun createAccount(view: View) {
        // Do something in response to button
        val intent = Intent(this, SignupActivity::class.java).apply {
        }
        startActivity(intent)
    }
}