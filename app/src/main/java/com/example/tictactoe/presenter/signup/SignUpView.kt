package com.example.tictactoe.presenter.signup

import android.app.Activity

interface SignUpView {

    /**
     * Gets the activity of the view.
     */
    fun getActivity(): Activity

    /**
     * Gets the email entered.
     */
    fun getEmail(): String

    /**
     * Gets the password entered.
     */
    fun getPassword(): String


}