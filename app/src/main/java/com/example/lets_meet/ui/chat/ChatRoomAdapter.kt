package com.example.lets_meet.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lets_meet.R
import com.example.lets_meet.model.ChatRoom

class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>() {

    private val chatRooms = mutableListOf<ChatRoom>()

    fun setChatRooms(rooms: List<ChatRoom>) {
        chatRooms.clear()
        chatRooms.addAll(rooms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chatroom, parent, false)
        return ChatRoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val chatRoom = chatRooms[position]
        holder.bind(chatRoom)
    }

    override fun getItemCount() = chatRooms.size

    class ChatRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chatRoomNameTextView: TextView = itemView.findViewById(R.id.chat_textview_title  )
        private val profileImageView: ImageView = itemView.findViewById(R.id.imageView_friend_profile)
        fun bind(chatRoom: ChatRoom) {
            chatRoomNameTextView.text = chatRoom.roomname // 여기서 채팅방 이름을 설정합니다. 필요에 따라 수정하세요.
            Glide.with(itemView.context).load(chatRoom.profileImageUrl).into(profileImageView)
        }
    }
}
