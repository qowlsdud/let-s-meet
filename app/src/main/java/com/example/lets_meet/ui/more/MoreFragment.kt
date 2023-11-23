package com.example.lets_meet.ui.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentMoreBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.example.lets_meet.ui.chat.ChatRoomAddActivity
import com.example.lets_meet.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser
        firestore = FirebaseFirestore.getInstance()
        val email = user?.email // 현재 로그인한 사용자의 이메일
        firestore.collection(email.toString()).get()
            .addOnSuccessListener { documents ->

                val friend = documents.toObjects(Friend::class.java)
                binding.nickname = friend[0].name
                binding.state = friend[0].state
            }
        binding.myTxtLogout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
