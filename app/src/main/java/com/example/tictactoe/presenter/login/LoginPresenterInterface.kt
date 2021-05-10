package com.example.tictactoe.presenter.login

import android.view.View

interface LoginPresenterInterface {

    /**
     * On activity start.
     */
    fun onStart()

    /**
     * On button click.
     */
    fun signInButtonClick(view: View)


    fun signUpButtonClick(view: View)
}