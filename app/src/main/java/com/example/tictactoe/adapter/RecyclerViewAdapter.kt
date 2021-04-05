package com.example.tictactoe.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tictactoe.R
import com.example.tictactoe.model.Firestore
import kotlinx.android.synthetic.main.recyclerview.view.*


class RecyclerViewAdapter (private val firestoreList: MutableList<Firestore>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((Firestore) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.stag_date.text = firestoreList[position].creationDate

    }

    override fun getItemCount(): Int {
        return firestoreList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(firestoreList[adapterPosition]!!)
            }
        }
    }
}