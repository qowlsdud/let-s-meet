package com.example.lets_meet.ui.friend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityMainBinding
import com.example.lets_meet.databinding.ActivityProfileBinding
import com.example.lets_meet.model.Email
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity: BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private lateinit var firestore: FirebaseFirestore
    private var friendemail: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding을 사용하여 레이아웃을 초기화합니다.
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firestore 인스턴스를 초기화합니다.
        firestore = FirebaseFirestore.getInstance()

        // 인텐트에서 "FRIEND_ID"를 가져옵니다.
        friendemail = intent.getStringExtra("FRIEND_email")

        // 프로필을 로드합니다.
        loadFriendProfile()
    }

    private fun loadFriendProfile() {
        val email = friendemail// 현재 로그인한 사용자의 이메일
        firestore.collection(email.toString()).get()
            .addOnSuccessListener { documents ->

                val friend = documents.toObjects(Friend::class.java)
                binding.profileName.text = friend[0].name
                binding.profileEmail.text = friend[0].email


            }
    }
}

