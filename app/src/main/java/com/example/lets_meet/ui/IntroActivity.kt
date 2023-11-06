package com.example.lets_meet.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.lets_meet.R
import com.example.lets_meet.ui.main.MainActivity
import com.example.lets_meet.ui.onboard.ViewPagerActivity
import com.google.firebase.auth.FirebaseAuth

class IntroActivity : AppCompatActivity() {

    // Firebase Authentication 인스턴스를 가져옵니다.
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler().postDelayed({
            // 현재 로그인한 사용자가 있는지 확인합니다.
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // 사용자가 로그인되어 있다면 MainActivity로 바로 이동합니다.
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            } else {
                // 로그인한 사용자가 없다면 ViewPagerActivity로 이동하여 로그인 또는 회원가입을 유도합니다.
                val onboardIntent = Intent(this, ViewPagerActivity::class.java)
                startActivity(onboardIntent)
                finish()
            }
        }, 1500)
    }
}
