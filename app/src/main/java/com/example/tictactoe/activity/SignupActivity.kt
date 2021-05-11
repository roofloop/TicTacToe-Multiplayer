package com.example.tictactoe.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.tictactoe.R
import com.example.tictactoe.presenter.login.LoginPresenter
import com.example.tictactoe.presenter.signup.SignUpPresenter
import com.example.tictactoe.presenter.signup.SignUpView
import com.example.tictactoe.utils.closeSoftKeyboard
import kotlinx.android.synthetic.main.activity_log_in.email_edit_text
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity(), SignUpView {

    companion object {
        private const val TAG: String = "SignUpActivity"
    }

    private val presenter: SignUpPresenter by lazy { SignUpPresenter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getEmail(): String {
        return signup_email_edit_text.text.toString()
    }

    override fun getPassword(): String {

        return if (password_edit_text1.text.toString() == password_edit_Text2.text.toString()) {

            password_edit_text1.text.toString()

        }else{
            password_edit_text1.error = "Password wrong"
            "Password mismatch"
        }

    }

    fun continueClick(view: View) {
        when (view.id) {
            R.id.continue_button -> presenter.signUpIntoFirebase(view)
            else -> {
            }
        }
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }

}