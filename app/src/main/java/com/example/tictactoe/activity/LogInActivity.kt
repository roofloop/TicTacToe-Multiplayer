package com.example.tictactoe.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.example.tictactoe.R
import com.example.tictactoe.presenter.login.LoginPresenter
import com.example.tictactoe.presenter.login.LoginView
import com.example.tictactoe.utils.closeSoftKeyboard
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity(), LoginView {

    companion object {
        private const val TAG: String = "LoginActivity"
    }

    private val presenter: LoginPresenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getEmail(): String {
        Log.e(TAG, "loginEmail.toString(): ${email_edit_text.text.toString()}")

        return email_edit_text.text.toString()
    }

    override fun getPassword(): String {
        return password_edit_text.text.toString()
    }

    fun loginButtonClick(view: View) {
        when (view.id) {
            R.id.login_button -> presenter.signInButtonClick(view)
            else -> {
            }
        }
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }

    fun signUpButtonClick(view: View) {
        when (view.id) {
            R.id.create_account_button -> presenter.signUpButtonClick(view)
            else -> {
            }
        }
        closeSoftKeyboard(this, view)
        view.requestFocusFromTouch()
    }

}
