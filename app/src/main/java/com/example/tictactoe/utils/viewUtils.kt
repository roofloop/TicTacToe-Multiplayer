package com.example.tictactoe.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun closeSoftKeyboard(context: Context, view: View?) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}