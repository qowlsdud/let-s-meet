package com.example.lets_meet.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.lets_meet.R
import com.example.lets_meet.ui.onboard.ViewPagerActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler().postDelayed({
            val intent = Intent(this, ViewPagerActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}