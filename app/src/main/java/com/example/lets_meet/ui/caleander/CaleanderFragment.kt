package com.example.lets_meet.ui.caleander

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentCaleanderBinding
import com.example.lets_meet.ui.base.BaseFragment
import java.util.*

class CaleanderFragment : BaseFragment<FragmentCaleanderBinding>(R.layout.fragment_caleander){
    private lateinit var calendarAdapter: CaleanderAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // BaseFragment의 onCreateView를 호출하여 binding을 초기화합니다.
        super.onCreateView(inflater, container, savedInstanceState)

        // DataBinding을 사용하여 RecyclerView 설정
        binding.rvCalendar.layoutManager = GridLayoutManager(context, 7) // 7 for a week's days
        calendarAdapter = CaleanderAdapter(generateCalendarDays())
        binding.rvCalendar.adapter = calendarAdapter

        // '+' 버튼에 대한 클릭 리스너 설정
        binding.plusCaleander.setOnClickListener {
            val bottomSheetDialogFragment = CaleanderDialogFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, CaleanderDialogFragment.TAG)
        }

        // 초기화된 binding의 root view를 반환합니다.
        return binding.root
    }

    private fun generateCalendarDays(): List<String> {
        val calendar = Calendar.getInstance()
        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val daysList = mutableListOf<String>()

        // Month 시작 전의 공백을 추가합니다.
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeekOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
        for (i in 1 until firstDayOfWeekOfMonth) {
            daysList.add("")
        }

        // 월의 날짜를 추가합니다.
        for (day in 1..maxDayOfMonth) {
            daysList.add(day.toString())
        }

        return daysList
    }
}
