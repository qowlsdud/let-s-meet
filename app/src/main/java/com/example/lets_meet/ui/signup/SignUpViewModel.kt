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
    val email = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
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
                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid
                    val data2 = hashMapOf(
                        "id" to uid.toString(),
                        "email" to email.value.toString(),
                        "name" to nickname.value.toString(),
                        "profileImageUrl" to "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMTgy/MDAxNjA0MjI4ODc1NDMw.Ex906Mv9nnPEZGCh4SREknadZvzMO8LyDzGOHMKPdwAg.ZAmE6pU5lhEdeOUsPdxg8-gOuZrq_ipJ5VhqaViubI4g.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%95%98%EB%8A%98%EC%83%89.jpg?type=w800",
                        "state" to "없음"
                    )
                    db.collection(email.value.toString())
                        .document("userinfo")
                        .set(data2)
                        .addOnSuccessListener {
                            Log.e("dd","success")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("dd",exception.toString())
                        }


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
    fun inputCheckPhoneNumber(): Boolean {
        if (phoneNumber.value.isNullOrBlank()) {
            return false
        }

        if (phoneNumber.value!!.length !in 11..11) {
            return false
        }

        return true
    }
}