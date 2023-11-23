package com.example.lets_meet.ui.chat
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityChatBinding
import com.example.lets_meet.model.Message
import com.example.lets_meet.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private val messagesList = mutableListOf<Message>()

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser ?: return
        val currentUserId = currentUser.uid

        messageAdapter = MessageAdapter(currentUserId)
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }

        val chatRoomId = intent.getStringExtra("chatRoomId") ?: return
        setupChatRoom(chatRoomId)

        binding.buttonSendMessage.setOnClickListener {
            val messageText = binding.edittextChatMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {

                val message = Message(
                    senderUid = currentUserId,
                    content = messageText,
                    senderName = currentUser.displayName ?: "Unknown User",
                    sended_date = getCurrentDateTime()
                )
                sendMessage(chatRoomId, message)
                binding.edittextChatMessage.text.clear()
            }
        }
    }

    private fun setupChatRoom(chatRoomId: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("chatrooms/$chatRoomId/messages")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { messagesList.add(it) }
                }
                messageAdapter.setMessages(messagesList)
                binding.recyclerViewChat.scrollToPosition(messagesList.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun sendMessage(chatRoomId: String, message: Message) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("chatrooms/$chatRoomId/messages")
        databaseReference.push().setValue(message)
            .addOnSuccessListener {
                // Message sent successfully
            }
            .addOnFailureListener {
                // Handle failed to send message
            }
    }
}