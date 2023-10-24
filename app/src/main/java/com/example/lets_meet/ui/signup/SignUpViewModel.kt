package com.example.lets_meet.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lets_meet.ui.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class SignUpViewModel: ViewModel() {

    var auth = FirebaseAuth.getInstance()

    val nickname = MutableLiveData<String>()    // 닉네임
    val email = MutableLiveData<String>()       // 이메일
    val password = MutableLiveData<String>()    // 비밀번호
    val passwordRe = MutableLiveData<String>()  // 비밀번호 다시 입력
    val errorMessage = MutableLiveData<String?>()
    val state = MutableLiveData<State>()

    fun sigininAndSignup() : Boolean{
        var k = false
        auth?.createUserWithEmailAndPassword(email.value.toString(),password.value.toString())
            ?.addOnCompleteListener {
                    task ->

                if (task.isSuccessful){
                    val db = FirebaseFirestore.getInstance()
                    state.value = State.OK
                    k = true
                    val data = hashMapOf(
                        "nickname" to nickname.value.toString()
                    )

                    db.collection(email.value.toString())
                        .document("usernickname")
                        .set(data)
                        .addOnSuccessListener {
                            Log.e("dd","success")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("dd",exception.toString())
                        }

                    Log.d("dd","ddd")

                }else if(task.exception?.message.isNullOrEmpty()){
                    Log.e("dd",task.exception?.message.toString())
                    state.value = State.FAIL
                }else{
                    Log.d("dd","ㄴㄴ")
                    sigininEmail()
                }
            }
        if(k){
            return true
        }else{
            return false
        }
    }
    fun sigininEmail(){

        auth?.createUserWithEmailAndPassword(email.value.toString(),password.value.toString())
            ?.addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    if(task.result?.user!=null){
                        state.value = State.OK
                    }
                    else{
                        state.value = State.FAIL
                    }
                }else{
                    errorMessage.value = task.exception?.message
                    state.value = State.FAIL
                }
            }
    }

    fun inputEmailCheck() : Boolean{

        if (email.value.isNullOrBlank()) {
            return false
        }
        if(!Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", email.value)){
            return false
        }
        return true
    }
    fun inputPasswordCheck() : Boolean{

        if (password.value.isNullOrBlank()) {
            return false
        }
        return true
    }
    fun inputPasswordReCheck() : Boolean{

        if (password.value.isNullOrBlank() || password.value != passwordRe.value) {
            return false
        }
        return true
    }

    fun inputCheckEmailPassword(): Boolean {
        if (email.value.isNullOrBlank()) {
            return false
        }

        if (password.value.isNullOrBlank()) {
            return false
        }

        if (passwordRe.value.isNullOrBlank()) {
            return false
        }

        if (password.value != passwordRe.value) {
            return false
        }

        return true
    }

    fun inputCheckNameNickName(): Boolean {
        if (nickname.value.isNullOrBlank()) {
            return false
        }

        if (nickname.value!!.length !in 2..8) {
            return false
        }

        return true
    }
}