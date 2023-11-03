package com.example.lets_meet.ui.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.R
import com.example.lets_meet.model.Friend

class FriendAdapter(private var friends: List<Friend>, private val onClick: (Friend) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View, val onClick: (Friend) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private var currentFriend: Friend? = null
        private val nameTextView: TextView = itemView.findViewById(R.id.textView_friend_name)
        private val profileImageView: ImageView = itemView.findViewById(R.id.imageView_friend_profile)
        // 기타 뷰 요소...

        init {
            itemView.setOnClickListener {
                currentFriend?.let {
                    onClick(it)
                }
            }
        }

        fun bind(friend: Friend) {
            currentFriend = friend
            nameTextView.text = friend.name
            // 프로필 이미지 로드, 예를 들어 Glide 라이브러리 사용
            // Glide.with(itemView.context).load(friend.profileImageUrl).into(profileImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.bind(friend)
    }

    override fun getItemCount(): Int = friends.size

    fun updateFriends(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }
}
