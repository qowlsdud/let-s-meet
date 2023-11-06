package com.example.lets_meet.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.lets_meet.R
import com.example.lets_meet.databinding.ActivityLoginBinding
import com.example.lets_meet.ui.base.BaseActivity
import com.example.lets_meet.ui.main.MainActivity
import com.example.lets_meet.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        // Firebase 인증 객체의 인스턴스를 가져옵니다.
        auth = FirebaseAuth.getInstance()

        binding.btnStart.setOnClickListener {
            val email = binding.loginEdtEmail.text.toString().trim()
            val password = binding.loginEdtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // 사용자 입력이 모두 채워져 있으면 로그인을 시도합니다.
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 로그인 성공 시, MainActivity로 이동합니다.
                            val loginintent = Intent(this, MainActivity::class.java)
                            startActivity(loginintent)
                            finish()
                        } else {
                            // 로그인 실패 시, 에러 메시지를 표시합니다.
                            Toast.makeText(this, "로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // 이메일 또는 비밀번호 입력란이 비어 있으면 경고 메시지를 띄웁니다.
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 기타 로그인 화면에 관련된 로직이나 기능 구현...
        // 예: 비밀번호 표시 토글, 비밀번호 재설정, 회원가입 화면으로의 이동 등
    }
}
