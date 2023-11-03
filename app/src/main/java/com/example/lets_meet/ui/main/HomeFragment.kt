package com.example.lets_meet.ui.main

import WeekAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentHomeBinding
import com.example.lets_meet.ui.base.BaseFragment
import com.example.lets_meet.ui.notice.NoticeActivity
import com.example.lets_meet.ui.setting.SettingActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Date
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){
    private lateinit var rvMain: RecyclerView
    private lateinit var weekAdapter: WeekAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        }
        // 초기값 설정: 오늘 날짜를 선택합니다.
        weekAdapter.selectedDate = Calendar.getInstance().time

        rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvMain.adapter = weekAdapter
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
}
