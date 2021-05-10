package com.example.tictactoe.presenter.login

import android.app.Activity

interface LoginView {

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