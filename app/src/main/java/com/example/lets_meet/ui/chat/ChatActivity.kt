package com.example.lets_meet.ui.chat
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityChatBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.model.Message
import com.example.lets_meet.ui.base.BaseActivity
import com.example.lets_meet.ui.caleander.CaleanderDialogFragment.Companion.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private val messagesList = mutableListOf<Message>()
    private lateinit var firestore: FirebaseFirestore
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

                val user = FirebaseAuth.getInstance().currentUser
                firestore = FirebaseFirestore.getInstance()
                val email = user?.email // 현재 로그인한 사용자의 이메일
                firestore.collection(email.toString()).get()
                    .addOnSuccessListener { documents ->

                        val friend = documents.toObjects(Friend::class.java)
                        Log.e("ddddd",friend[0].name)
                        val message = Message(
                            senderUid = currentUserId,
                            content = messageText,
                            senderName = friend[0].name,
                            sended_date = getCurrentDateTime()
                        )
                        sendMessage(chatRoomId, message)
                        binding.edittextChatMessage.text.clear()
                    }

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
        val database = FirebaseDatabase.getInstance()
        val messagesRef = database.getReference("chatrooms/$chatRoomId/messages")

        // 새 메시지를 messages 하위에 추가합니다.
        messagesRef.push().setValue(message).addOnCompleteListener { messageTask ->
            if (messageTask.isSuccessful) {
                // 메시지가 성공적으로 추가되면 lastMessage만을 업데이트합니다.
                val lastMessageRef = database.getReference("chatrooms/$chatRoomId/lastMessage")
                lastMessageRef.setValue(message.content).addOnCompleteListener { lastMessageTask ->
                    if (lastMessageTask.isSuccessful) {
                        Log.d(TAG, "Last message updated successfully")
                    } else {
                        // lastMessage 업데이트 실패 시 처리
                        Log.e(TAG, "Failed to update last message", lastMessageTask.exception)
                    }
                }
            } else {
                // 메시지 추가 실패 시 처리
                Log.e(TAG, "Failed to send message", messageTask.exception)
            }
        }
    }

}