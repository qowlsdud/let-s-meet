package com.example.lets_meet.ui.caleander

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lets_meet.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CaleanderDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caleander_dialog, container, false)
    }

    companion object {
        const val TAG = "CaleanderDialogFragment"
    }
}