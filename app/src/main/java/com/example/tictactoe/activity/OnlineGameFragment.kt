package com.example.tictactoe.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentOnlineGameBinding
import com.example.tictactoe.model.FirestoreModel
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OnlineGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class OnlineGameFragment : Fragment() {

    private lateinit var nameText : TextView
    private lateinit var binding: FragmentOnlineGameBinding
    private lateinit var firestoreHelper: FirestoreModel
    private lateinit var uid : String
    private lateinit var creatorId : String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestoreHelper = FirestoreModel()
        val user = FirebaseAuth.getInstance().currentUser
        uid = user.uid

        creatorId = activity!!.intent.getStringExtra("creatorId").toString()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_online_game, container, false)

        binding = FragmentOnlineGameBinding.inflate(layoutInflater)


        return view
    }

    fun setText(text: String) {
        nameText.text = text
        Toast.makeText(requireContext(), "Text changed", Toast.LENGTH_SHORT).show()
    }

}