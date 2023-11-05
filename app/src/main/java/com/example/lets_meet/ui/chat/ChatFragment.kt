package com.example.lets_meet.ui.chat
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class ChatFragment : Fragment() {
    private lateinit var btnAddChatRoom: Button
//    private lateinit var btnSignOut: Button
//    private lateinit var firebaseDatabase: DatabaseReference
    private lateinit var recyclerChatRoom: RecyclerView
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        initializeView()
//        setupRecycler()
        return binding.root
    }

    private fun initializeView() {
        try {
//            firebaseDatabase = FirebaseDatabase.getInstance().getReference("ChatRoom")
            btnAddChatRoom = binding.btnNewMessage
            recyclerChatRoom = binding.recyclerChatrooms
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "화면 초기화 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show()
        }
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun setupRecycler() {
//        recyclerChatRoom.layoutManager = LinearLayoutManager(context)
//        recyclerChatRoom.adapter = RecyclerChatRoomsAdapter(requireContext())
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
