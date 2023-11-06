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
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentChatBinding
import com.example.lets_meet.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
