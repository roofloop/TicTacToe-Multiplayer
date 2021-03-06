package com.example.tictactoe.utils

/**
 * Return the local part of the email address for the database path of
 * Firebase.

 */
fun getUserFromEmailForFirebase(email: String?): String {
    var result = email?.split("@")?.get(0) ?: ""
    result = result.replace("\\.|#|\\$|\\[|]".toRegex(), "")
    return result
}