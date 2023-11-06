package com.example.lets_meet.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lets_meet.model.Email
import com.example.lets_meet.model.Friend
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    private val _today = MutableLiveData<Date>()
    // 'today' LiveData가 Date 변경을 관찰하고 문자열로 변환합니다.
    val today: LiveData<String> = MutableLiveData<String>().apply {
        _today.observeForever { date ->
            value = SimpleDateFormat("MM월 dd일 EEEE", Locale.KOREAN).format(date)
        }
    }

    init {
        _today.value = Date() // 현재 날짜로 초기화
    }
}
