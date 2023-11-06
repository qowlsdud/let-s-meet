package com.example.lets_meet.ui.caleander

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityCaleanderAddBinding
import com.example.lets_meet.databinding.ActivityMainBinding
import com.example.lets_meet.model.Event
import com.example.lets_meet.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CaleanderAddActivity : BaseActivity<ActivityCaleanderAddBinding>(R.layout.activity_caleander_add){
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        firestore = FirebaseFirestore.getInstance()
        var currentDate = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("MM월 dd일", Locale.KOREA)
        var dateString: String = dateFormat.format(currentDate)
        binding.startdate.text = dateString
        binding.enddate.text = dateString

        binding.btnStart.setOnClickListener {
            SaveEvent()
        }
    }

    private fun SaveEvent() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val data = hashMapOf(
            "title" to binding.caleanderTitleEt.text.toString(),
            "date" to binding.startdate.text.toString(),
            "starttime" to binding.starttime.text.toString(),
            "endtime" to binding.endtime.text.toString(),
            "public" to binding.publicSwitch.isChecked,
            "notice" to binding.noticeSwitcg.isChecked
        )
        firestore.collection(user?.email.toString())
            .document("event")
            .collection("event")
            .add(data)
            .addOnSuccessListener {
                Log.e("dd","success")
                finish()
            }
            .addOnFailureListener { exception ->
                Log.e("dd",exception.toString())
            }
    }

}