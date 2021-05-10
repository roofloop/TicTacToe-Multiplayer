package com.example.tictactoe.presenter.login

import android.util.Log
import android.view.View
import com.example.tictactoe.R
import com.example.tictactoe.activity.MainActivity
import com.example.tictactoe.data.FirebaseClient
import com.example.tictactoe.manager.NavigationManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginView) : LoginPresenterInterface {

    companion object {
        private const val TAG: String = "LoginPresenter"
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDatabase by lazy { FirebaseClient(view.getActivity()) }

    override fun onStart() {
        loadMainActivity()
    }

    override fun signInButtonClick(view: View) {
        when (view.id) {
            R.id.login_button -> {
                signInIntoFirebase(view)
            }
            else -> return
        }
    }

    override fun signUpButtonClick(view: View) {
        when (view.id) {
            R.id.textView2 -> {
                signUpIntoFirebase(view)
            }
            else -> return
        }    }

    private fun signInIntoFirebase(view: View) {
        Log.i(TAG, "Trying to Sign in into Firebase" + this.view.getEmail())


        firebaseAuth.signInWithEmailAndPassword(this.view.getEmail(), this.view.getPassword())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loadMainActivity()
                    } else {

                        if (it.exception != null) {
                            Snackbar.make(view, it.exception?.message ?: view.context.getString(R.string.firebase_sign_in_fail), Snackbar.LENGTH_SHORT).show()
                            Log.e(TAG, it.exception?.message, it.exception)
                        }
                    }
                }
    }

    private fun signUpIntoFirebase(view: View) {
        Log.i(TAG, "Trying to Sign up into Firebase")
        firebaseAuth.createUserWithEmailAndPassword(this.view.getEmail(), this.view.getPassword())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loadMainActivity()
                    } else {
                        Snackbar.make(view, it.exception?.message ?: view.context.getString(R.string.firebase_sign_up_fail), Snackbar.LENGTH_SHORT).show()

                    }
                }
    }

    private fun loadMainActivity() {
        val currentUser = firebaseAuth?.currentUser ?: return
        NavigationManager()
                .finishingCurrent()
                .putExtras(firebaseDatabase.getUserBundle(currentUser))
                .goTo(view.getActivity(), MainActivity::class.java)
    }
}