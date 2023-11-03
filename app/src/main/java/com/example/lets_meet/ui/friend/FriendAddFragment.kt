package com.example.lets_meet.ui.friend

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentFriendAddBinding
import com.example.lets_meet.databinding.FragmentFriendBinding
import com.example.lets_meet.databinding.FragmentHomeBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore

class FriendAddFragment : BaseFragment<FragmentFriendAddBinding>(R.layout.fragment_friend_add){
    private lateinit var firestore: FirebaseFirestore
    private var friendId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment에서는 onCreate 안에서 setContentView를 사용하지 않습니다.

        firestore = FirebaseFirestore.getInstance()
        // Fragment에서는 인텐트 대신 arguments를 사용합니다.
        friendId = arguments?.getString("FRIEND_ID")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 레이아웃을 인플레이트합니다.
        val view = inflater.inflate(R.layout.fragment_friend_add, container, false)

        // 프로필 로드는 onCreateView가 호출된 후, 또는 onViewCreated에서 해야 합니다.
        // ...

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 뷰가 생성된 이후에 프로필을 로드할 수 있습니다.
        loadFriendProfile(friendId)
    }

    private fun loadFriendProfile(friendId: String?) {
        friendId?.let {
            firestore.collection("friends").document(it).get()
                .addOnSuccessListener { documentSnapshot ->
                    val friend = documentSnapshot.toObject(Friend::class.java)
                    // 프로필 데이터를 화면에 표시, 뷰에 직접 접근해서 데이터를 설정할 수 있습니다.
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error loading profile", e)
                }
        }
    }

    // 나머지 코드...
}
