package com.example.lets_meet.ui.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentFriendSearchBinding
import com.example.lets_meet.databinding.FragmentHomeBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore

class FriendSearchFragment : BaseFragment<FragmentFriendSearchBinding>(R.layout.fragment_friend_search) {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: FriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter = FriendAdapter(emptyList()) { friend ->
            // 여기서 친구 프로필로 넘어가는 로직 구현
        }
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearch.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchFriends(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchFriends(it) }
                return false
            }
        })
    }

    private fun searchFriends(query: String) {
        // Firestore에서 query에 해당하는 친구들을 검색하고 결과를 RecyclerView에 표시
        // 예를 들어 이름으로 필터링
        firestore.collection("friends")
            .whereEqualTo("name", query)
            .get()
            .addOnSuccessListener { documents ->
                val friendsList = documents.toObjects(Friend::class.java)
                adapter.updateFriends(friendsList)
            }
            .addOnFailureListener { exception ->
                // 오류 처리
            }
    }

}
