package com.example.lets_meet.ui.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lets_meet.ui.login.LoginActivity
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityViewPagerBinding
import com.example.lets_meet.ui.signup.SignUpActivity

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding
    private lateinit var ViewPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        // 뷰 바인딩, 어댑터 실행
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        initAdapter()

        setContentView(binding.root)

        // indicator 연결
        binding.dotsIndicator.setViewPager2(binding.boardVp)

        // 시작하기 버튼 눌렀을 때 스킵하고 회원가입 창으로 이동
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        binding.btnLoginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun initAdapter() {
        //Adapter 안에 ViewPager2 상에 띄워줄 fragmentList 생성
        val fragmentList = listOf(FirstFragment(),SecondFragment(),ThirdFragment())

        //ViewPagerAdapter 초기화
        ViewPagerAdapter = PagerAdapter(this)
        ViewPagerAdapter.fragments.addAll(fragmentList)

        //ViewPager2와 Adapter 연동
        binding.boardVp.adapter = ViewPagerAdapter

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.boardVp.currentItem == 0) {
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.boardVp.currentItem = binding.boardVp.currentItem - 1
        }
    }
}