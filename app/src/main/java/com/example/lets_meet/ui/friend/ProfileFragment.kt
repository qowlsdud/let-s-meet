package com.example.lets_meet.ui.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentHomeBinding
import com.example.lets_meet.databinding.FragmentProfileBinding
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile){

    private lateinit var firestore: FirebaseFirestore
    private var friendId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firestore = FirebaseFirestore.getInstance()

        // friendId는 어떤 방법으로 전달받게 될지에 따라 달라질 수 있습니다.
        // 예를 들어, arguments를 통해 전달 받을 수 있습니다.
        arguments?.let {
            friendId = it.getString("FRIEND_ID")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFriendProfile(friendId)
    }

    private fun loadFriendProfile(friendId: String?) {
        friendId?.let { id ->
            firestore.collection("friends").document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    val friend = documentSnapshot.toObject(Friend::class.java)
                    // 데이터를 뷰에 바인딩합니다.
                    friend?.let { f ->
                        binding.profileName.text = f.name
                        binding.profileEmail.text = f.email
                        // 기타 프로필 정보를 바인딩...
                    }
                }
                .addOnFailureListener { e ->
                    // 에러 처리
                }
        }
    }

}
