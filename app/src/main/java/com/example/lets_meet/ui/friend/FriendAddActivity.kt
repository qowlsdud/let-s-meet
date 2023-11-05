package com.example.lets_meet.ui.friend

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityFriendAddBinding
import com.example.lets_meet.databinding.ActivityMainBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseActivity
import com.example.lets_meet.ui.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FriendAddActivity : BaseActivity<ActivityFriendAddBinding>(R.layout.activity_friend_add) {
    private lateinit var firestore: FirebaseFirestore
    private var friendId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buttonSaveFriend.setOnClickListener {
            val friendemail = binding.editTextFriendEmail.text.toString()
            val db = FirebaseFirestore.getInstance()
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            val data1 = hashMapOf(
                "email" to user?.email.toString(),
            )
            db.collection(friendemail)
                .document("userinfo")
                .collection("friends")
                .document(user?.uid.toString())
                .set(data1)
                .addOnSuccessListener {
                    Log.e("dd","success")
                }
                .addOnFailureListener { exception ->
                    Log.e("dd",exception.toString())
                }
            val data2 = hashMapOf(
                "email" to friendemail,
            )
            db.collection(user?.email.toString()).document("userinfo")
                .collection("friends")
                .document(user?.uid.toString())
                .set(data2)
                .addOnSuccessListener {
                    Log.e("dd","success")
                }
                .addOnFailureListener { exception ->
                    Log.e("dd",exception.toString())
                }
        }
    }

}
