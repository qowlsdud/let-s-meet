package com.example.lets_meet.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ItemMessageReceivedBinding
import com.example.lets_meet.databinding.ItemMessageSentBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageAdapter(private val currentUserId: String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var messages: MutableList<Message> = mutableListOf()
    private lateinit var firestore: FirebaseFirestore

    companion object {
        const val VIEW_TYPE_SENT = 0
        const val VIEW_TYPE_RECEIVED = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> SentMessageViewHolder(ItemMessageSentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_RECEIVED -> ReceivedMessageViewHolder(ItemMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderUid == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> {
                if (message.senderUid == currentUserId) {
                    holder.bind(message)
                }
            }
            is ReceivedMessageViewHolder -> {
                if (message.senderUid != currentUserId) {
                    holder.bind(message)
                }
            }
        }
    }

    fun setMessages(newMessages: List<Message>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    inner class SentMessageViewHolder(private val binding: ItemMessageSentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            Log.d("MessageAdapter", "Binding sent message: ${message.content}")
            binding.textViewSentMessage.text = message.content
        }
    }

    inner class ReceivedMessageViewHolder(private val binding: ItemMessageReceivedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            val user = FirebaseAuth.getInstance().currentUser
            firestore = FirebaseFirestore.getInstance()
            val email = user?.email // 현재 로그인한 사용자의 이메일
            firestore.collection(email.toString()).get()
                .addOnSuccessListener { documents ->

                    val friend = documents.toObjects(Friend::class.java)
                    val url = friend[0].profileImageUrl
                    binding.textViewReceivedMessage.text = message.content
                    binding.textViewSenderName.text = message.senderName
                    Glide.with(itemView.context).load(url).into(itemView.findViewById(R.id.imageViewProfile))
                }

        }
    }
}
