package com.example.lets_meet.ui.state

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.lets_meet.R
import com.example.lets_meet.model.State

// StateChangeActivity.kt
class StateChangeActivity : AppCompatActivity() {

    private lateinit var adapter: StateAdapter
    private val stateList = mutableListOf<State>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_change)

        stateList.addAll(getInitialStates())

        adapter = StateAdapter(this, stateList)
        val listView: ListView = findViewById(R.id.lvStates)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val state = stateList[position]
        }
    }
    private fun getInitialStates(): List<State> {
        // 데이터 소스에서 상태 목록을 가져오거나 초기 데이터를 생성합니다.
        return listOf(State("없음"))
    }

    // ...
}
