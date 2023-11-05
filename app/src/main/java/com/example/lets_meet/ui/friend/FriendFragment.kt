package com.example.lets_meet.ui.friend

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentFriendBinding
import com.example.lets_meet.model.Email
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.example.lets_meet.ui.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FriendFragment : BaseFragment<FragmentFriendBinding>(R.layout.fragment_friend) {

    private lateinit var adapter: FriendAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        binding.headerFriend.setMenu1IconClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.headerFriend.setMenu2IconClickListener {
            val intent = Intent(context, FriendAddActivity::class.java)
            startActivity(intent)
        }
        binding.headerFriend.setMenu3IconClickListener {
            val intent = Intent(context, FriendSearchActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()
        fetchFriends()
    }

    private fun setupRecyclerView() {
        adapter = FriendAdapter(emptyList()) { friend ->
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("FRIEND_email", friend.email)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }
    private fun fetchFriends() {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email // 현재 로그인한 사용자의 이메일
        firestore.collection(email.toString())
            .document("userinfo")
            .collection("friends").get()
            .addOnSuccessListener { documents ->
                val friendsEmailList = documents.toObjects(Email::class.java)

                var friendsList = mutableListOf<Friend>()
                for (i in friendsEmailList) {
                    firestore.collection(i.email.toString()).get()
                        .addOnSuccessListener { documents ->

                            val friend = documents.toObjects(Friend::class.java)
                            adapter.updateFriends(friend)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}
