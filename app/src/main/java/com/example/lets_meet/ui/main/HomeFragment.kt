package com.example.lets_meet.ui.main

import WeekAdapter
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentHomeBinding
import com.example.lets_meet.model.Email
import com.example.lets_meet.model.Event
import com.example.lets_meet.model.Friend
import com.example.lets_meet.ui.state.StateChangeActivity
import com.example.lets_meet.ui.base.BaseFragment
import com.example.lets_meet.ui.caleander.CaleanderDialogFragment.Companion.TAG
import com.example.lets_meet.ui.friend.FriendAdapter
import com.example.lets_meet.ui.friend.ProfileActivity
import com.example.lets_meet.ui.notice.NoticeActivity
import com.example.lets_meet.ui.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){
    private lateinit var rvMain: RecyclerView
    private lateinit var weekAdapter: WeekAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FriendAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var eventAdapter: EventAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        firestore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val user = FirebaseAuth.getInstance().currentUser
        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner  // LiveData가 UI를 업데이트할 수 있도록 설정
        firestore.collection(user?.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                val friendsEmailList = documents.toObjects(Friend::class.java)

                binding.state = friendsEmailList[0].state
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        binding.headerHome.setMenu1IconClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.headerHome.setMenu2IconClickListener {
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }

        rvMain = binding.rvMain
        val dates = getCurrentWeekDates()

        weekAdapter = WeekAdapter(dates) { selectedDate ->
            // 클릭된 날짜를 선택된 날짜로 설정합니다.
            weekAdapter.selectedDate = selectedDate
            // 데이터 변경을 반영하기 위해 어댑터에게 알려줍니다.
            weekAdapter.notifyDataSetChanged()
            setupEventRecyclerView()
            fetchEventsForSelectedDate(selectedDate)
            setupRecyclerView()
            fetchFriends()
        }
        // 초기값 설정: 오늘 날짜를 선택합니다.
        weekAdapter.selectedDate = Calendar.getInstance().time

        binding.buttonChange.setOnClickListener {
            val intent = Intent(context, StateChangeActivity::class.java)
            startActivity(intent)
        }
        rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvMain.adapter = weekAdapter
        setupRecyclerView()
        fetchFriends()
    }

    private fun getCurrentWeekDates(): List<Date> {
        val dates = mutableListOf<Date>()
        val calendar = Calendar.getInstance()

        // 주의 첫 요일로 설정 (일요일)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1)
        }

        // 일주일의 날짜 추가
        for (i in 0..6) {
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }

        return dates
    }
    private fun setupRecyclerView() {
        adapter = FriendAdapter(emptyList()) { friend ->
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("FRIEND_email", friend.email)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }
    private fun fetchFriends() {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email // 현재 로그인한 사용자의 이메일
        firestore.collection(email.toString())
            .document("userinfo")
            .collection("friends").get()
            .addOnSuccessListener { documents ->
                val friendsEmailList = documents.toObjects(Email::class.java)

                var friendsList = mutableListOf<Friend>()
                for (i in friendsEmailList) {
                    firestore.collection(i.email.toString()).get()
                        .addOnSuccessListener { documents ->

                            val friend = documents.toObjects(Friend::class.java)
                            adapter.updateFriends(friend)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
    private fun setupEventRecyclerView() {

        eventAdapter = EventAdapter(emptyList(), weekAdapter.selectedDate.toString())
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
        binding.recyclerView2.adapter = eventAdapter
    }

    // 선택된 날짜에 맞는 이벤트를 가져옵니다.
    private fun fetchEventsForSelectedDate(selectedDate: Date) {
        val user = FirebaseAuth.getInstance().currentUser
        // `selectedDate`를 "MM월 dd일" 포맷으로 문자열로 변환
        val dateFormat = SimpleDateFormat("MM월 dd일", Locale.KOREA)
        val formattedDate = dateFormat.format(selectedDate)


        // Firestore에서 이벤트를 가져오는 코드 작성
        firestore.collection(user?.email.toString())
            .document("event")
            .collection("event")
            .get()
            .addOnSuccessListener { documents ->
                Log.e("ddd",documents.toObjects(Event::class.java).toString())
                val events = documents.toObjects(Event::class.java)
                for (i in events) {
                    if(i.date == formattedDate){

                            val friend = documents.toObjects(Friend::class.java)
                            adapter.updateFriends(friend)
                        }
                }
                eventAdapter = EventAdapter(events, formattedDate)
                binding.recyclerView2.adapter = eventAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting events: ", exception)
            }
    }



}
