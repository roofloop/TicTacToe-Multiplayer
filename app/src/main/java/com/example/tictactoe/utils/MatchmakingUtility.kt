package com.example.tictactoe.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

object MatchmakingUtility {
    private val db = Firebase.firestore
    private val liveDataSearching: MutableLiveData<Boolean> = MutableLiveData()


    fun waitForUserToJoin(): LiveData<Boolean> {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user.uid

        val docRef = db.collection("Games").document(uid)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {

                val searching = snapshot.get("searching") as Boolean
                liveDataSearching.postValue(searching)
            }

        };return liveDataSearching
    }

}