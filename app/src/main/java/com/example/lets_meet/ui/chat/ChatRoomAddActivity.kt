package com.example.lets_meet.ui.chat

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityChatRoomAddBinding
import com.example.lets_meet.model.ChatRoom
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatRoomAddActivity  : BaseActivity<ActivityChatRoomAddBinding>(R.layout.activity_chat_room_add)  {
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupCreateButton()
        fetchFriends()
    }
    private fun fetchFriends() {
        // 예시 코드입니다. 실제 Firebase 데이터베이스 구조에 따라 변경해야 할 수 있습니다.
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friendsList = mutableListOf<Friend>()
                for (snapshot in dataSnapshot.children) {
                    val friend = snapshot.getValue(Friend::class.java)
                    friend?.let { friendsList.add(it) }
                }
                setupRecyclerView(friendsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스 오류 처리
            }
        })
    }

    // RecyclerView 설정하는 메서드
    private fun setupRecyclerView(friendsList: List<Friend>) {
    }
    private fun setupCreateButton() {
        binding.createButton.setOnClickListener {
            val chatRoomName = binding.chatRoomNameEditText.text.toString()
            if (chatRoomName.isNotEmpty()) {
                createChatRoom(chatRoomName)
            } else {
                // 사용자에게 채팅방 이름을 입력하라는 메시지 표시
            }
        }
    }

    private fun createChatRoom(name: String) {
        val newRoomId = database.child("chatrooms").push().key
        newRoomId?.let {
            val users = mapOf("test1234" to true, "test1010" to true) // 샘플 사용자 목록, 실제 구현에서는 현재 사용자 정보를 사용해야 함
            val chatRoom = ChatRoom(
                roomId = it,
                roomname = name,
                users = users,
                profileImageUrl = "https://i.namu.wiki/i/Bge3xnYd4kRe_IKbm2uqxlhQJij2SngwNssjpjaOyOqoRhQlNwLrR2ZiK-JWJ2b99RGcSxDaZ2UCI7fiv4IDDQ.webp",
                lastMessage = "채팅방이 생성되었습니다."
            )
            database.child("chatrooms").child(it).setValue(chatRoom)
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener {
                    // 채팅방 생성 실패 처리
                }
        }
    }
}
