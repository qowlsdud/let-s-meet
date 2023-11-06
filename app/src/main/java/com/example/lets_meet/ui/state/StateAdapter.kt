package com.example.lets_meet.ui.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.lets_meet.R
import com.example.lets_meet.model.State

// StateAdapter.kt
class StateAdapter(context: Context, states: List<State>) :
    ArrayAdapter<State>(context, 0, states) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 재사용 가능한 View를 받거나 새로 만듭니다.
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_state, parent, false)

        // 현재 상태를 가져옵니다.
        val state = getItem(position)

        // 텍스트와 아이콘 등을 설정합니다.
        val textView = view.findViewById<TextView>(R.id.tvStateMessage)
        val iconView = view.findViewById<ImageView>(R.id.ivStateIcon)

        textView.text = state?.message
        iconView.visibility = if (state?.isEdited == true) View.VISIBLE else View.INVISIBLE

        return view
    }
}
