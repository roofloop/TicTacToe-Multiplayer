package com.example.tictactoe.presenter.signup

import android.util.Log
import android.view.View
import com.example.tictactoe.R
import com.example.tictactoe.activity.MainActivity
import com.example.tictactoe.activity.SignupActivity
import com.example.tictactoe.data.FirebaseClient
import com.example.tictactoe.manager.NavigationManager
import com.example.tictactoe.presenter.login.LoginPresenterInterface
import com.example.tictactoe.presenter.login.LoginView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpPresenter(private val view: SignUpView) : SignUpPresenterInterface {

    companion object {
        private const val TAG: String = "SignUpPresenter"
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDatabase by lazy { FirebaseClient(view.getActivity()) }

     fun signUpIntoFirebase(view: View) {
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
        val currentUser = firebaseAuth.currentUser ?: return
        NavigationManager()
                .finishingCurrent()
                .putExtras(firebaseDatabase.getUserBundle(currentUser))
                .goTo(view.getActivity(), MainActivity::class.java)
    }

}