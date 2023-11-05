package com.example.lets_meet.ui.friend

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityFriendSearchBinding
import com.example.lets_meet.databinding.ActivityMainBinding
import com.example.lets_meet.model.Email
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FriendSearchActivity: BaseActivity<ActivityFriendSearchBinding>(R.layout.activity_friend_search) {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding 초기화
        binding = ActivityFriendSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firestore 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        // RecyclerView와 SearchView 설정
        setupRecyclerView()
        fetchFriends()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter = FriendAdapter(emptyList()) { friend ->
            // 친구 프로필로 넘어가는 로직 구현
            // 예시: Intent로 상세 액티비티 시작
        }
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearch.adapter = adapter
    }private fun fetchFriends() {
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
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
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
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email // 현재 로그인한 사용자의 이메일
        Log.e("dddd", query)
        adapter.updateFriends(emptyList())
        firestore.collection(email.toString())
            .document("userinfo")
            .collection("friends")
            .get()
            .addOnSuccessListener { documents ->
                val friendsEmailList = documents.toObjects(Email::class.java)

                var friendsList = mutableListOf<Friend>()
                for (i in friendsEmailList) {
                    firestore.collection(i.email)
                        .get()
                        .addOnSuccessListener { documents ->

                            val friend = documents.toObjects(Friend::class.java)
                            if(friend[0].name.contains(query)) {
                                    adapter.updateFriends(friend)
                            }
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }


    }

}
