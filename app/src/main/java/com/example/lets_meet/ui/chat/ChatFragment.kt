package com.example.lets_meet.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.databinding.FragmentChatBinding
import com.example.lets_meet.model.ChatRoom
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.headerChat.setMenu2IconClickListener {
            val intent = Intent(context, ChatRoomAddActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()
        fetchChatRooms()

        return binding.root
    }

    private fun setupRecyclerView() {
        chatRoomAdapter = ChatRoomAdapter()
        binding.recyclerChatrooms.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRoomAdapter
        }
    }

    private fun fetchChatRooms() {
        database.child("chatrooms").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chatRooms = mutableListOf<ChatRoom>()
                for (snapshot in dataSnapshot.children) {
                    val chatRoom = snapshot.getValue(ChatRoom::class.java)
                    chatRoom?.let {
                        chatRooms.add(it)
                    }
                }
                chatRoomAdapter.setChatRooms(chatRooms)
            }override fun onCancelled(databaseError: DatabaseError) {
                // 채팅방 목록 불러오기 실패 처리
                // 예: 로그 출력, 사용자에게 오류 메시지 표시
            }
        })
    }
}