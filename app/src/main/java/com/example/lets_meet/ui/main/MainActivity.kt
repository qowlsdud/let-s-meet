package com.example.lets_meet.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityMainBinding
import com.example.lets_meet.ui.base.BaseActivity
import com.example.lets_meet.ui.caleander.CaleanderFragment
import com.example.lets_meet.ui.chat.ChatFragment
import com.example.lets_meet.ui.friend.FriendFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bnv_main = binding.bnvMain

        bnv_main.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.main_menu_home -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_main, homeFragment).commit()
                    }
                    R.id.main_menu_user -> {
                        val friendFragment = FriendFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_main, friendFragment).commit()
                    }
                    R.id.main_menu_calendar -> {
                        val caleanderFragment = CaleanderFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_main, caleanderFragment).commit()
                    }
                    R.id.main_menu_chat -> {
                        val chatFragment = ChatFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_main, chatFragment).commit()
                    }
                    R.id.main_menu_more -> {
                        val moreFragment = MoreFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_main, moreFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.main_menu_home
        }

    }
}