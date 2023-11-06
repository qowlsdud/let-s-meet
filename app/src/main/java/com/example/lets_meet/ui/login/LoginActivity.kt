package com.example.lets_meet.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityCaleanderAddBinding
import com.example.lets_meet.databinding.ActivityLoginBinding
import com.example.lets_meet.ui.base.BaseActivity
import com.example.lets_meet.ui.main.MainActivity
import com.example.lets_meet.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity  : BaseActivity<ActivityLoginBinding>(R.layout.activity_login){

    lateinit var auth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auto = getSharedPreferences("autoskip", MODE_PRIVATE)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,
            R.layout.activity_login
        )
        val autoSkipEdit = auto.edit()
        autoSkipEdit.putString("autoskip", "True")
        autoSkipEdit.commit()
        auth = FirebaseAuth.getInstance()
        val emailInput = findViewById<EditText>(R.id.login_edt_email)
        val passwordInput = findViewById<EditText>(R.id.login_edt_password)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            auth.signInWithEmailAndPassword(
                emailInput.text.toString(),
                passwordInput.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val loginintent = Intent(this, MainActivity::class.java)
                        startActivity(loginintent)
                    } else {
                        Toast.makeText(this, "실패!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
//        binding.forgotPassword.setOnClickListener{
//            val loginintent = Intent(this, SignUpActivity::class.java)
//            startActivity(loginintent)
//        }
//        binding.loginCheckPass.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                binding.loginEtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            } else {
//                binding.loginEtPass.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//        }


    }
}