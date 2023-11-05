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

class CaleanderAddActivity : BaseActivity<ActivityCaleanderAddBinding>(R.layout.activity_caleander_add){
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        firestore = FirebaseFirestore.getInstance()

//        binding.buttonSubmit.setOnClickListener {
//            submitEvent()
//        }

//        binding.editTextDate.setOnClickListener {
//            showDatePickerDialog()
//        }
//
//        binding.editTextTime.setOnClickListener {
//            showTimePickerDialog()
//        }
    }

    private fun showDatePickerDialog() {
        // DatePickerDialog 구현
    }

    private fun showTimePickerDialog() {
        // TimePickerDialog 구현
    }

//    private fun submitEvent() {
//
//        val title = binding.editTextTitle.text.toString().trim()
//        val starttime = binding.editTextLocation.text.toString().trim()
//        val date = binding.editTextDate.text.toString().trim()
//        val endtime = binding.editTextTime.text.toString().trim()
//        val description = binding.editTextDescription.text.toString().trim()
//
//        if (title.isNotEmpty() && date.isNotEmpty() && starttime.isNotEmpty() && endtime.isNotEmpty() ){
//            val event = Event(title, starttime, date, endtime, description)
//            Log.e("dddddd", event.toString())
//            val user = FirebaseAuth.getInstance().currentUser
//            firestore.collection(user?.email.toString())
//                .document("userinfo")
//                .collection("events")
//                .add(event)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show()
//                    finish()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this, "Failed to add event", Toast.LENGTH_LONG).show()
//                }
//        } else {
//            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
//        }
//    }
}