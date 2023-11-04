package com.example.lets_meet.ui.friend

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentFriendBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore

class FriendFragment : BaseFragment<FragmentFriendBinding>(R.layout.fragment_friend) {

    private lateinit var adapter: FriendAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        setupRecyclerView()
        fetchFriends()
    }

    private fun setupRecyclerView() {
        adapter = FriendAdapter(emptyList()) { friend ->
            val intent = Intent(context, ProfileFragment::class.java)
            intent.putExtra("FRIEND_ID", friend.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchFriends() {
        firestore.collection("friends").get()
            .addOnSuccessListener { documents ->
                val friendsList = documents.toObjects(Friend::class.java)
                adapter.updateFriends(friendsList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}
