package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    // Entry point of the Firebase Authentication SDK.
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Different user inputs relevant to account creation
        val editEmail = (findViewById<TextInputEditText>(R.id.email_edit_text))
        val editPassword = (findViewById<TextInputEditText>(R.id.password_edit_text))

        val confirmButton = findViewById<Button>(R.id.login_button)
        confirmButton.setOnClickListener { createAccount(editEmail.text.toString(), editPassword.text.toString()) }

    }

    private fun createAccount( email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")


                    Log.i(TAG, "Completed: " + task.isComplete)

                    val user = auth.currentUser
                    // Do something in response to button
                    val intent = Intent(this, LogInActivity::class.java).apply {
                    }
                    startActivity(intent)
                    finish()
                } else {

                    // If sign in fails, display a message to the user.
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnFailureListener { e ->
                            Log.e(
                                TAG,
                                "Unable to create user" + e.localizedMessage,
                                e
                            )

                            Toast.makeText(this, e.localizedMessage,
                                Toast.LENGTH_LONG).show()
                        }
                }
            }

    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}