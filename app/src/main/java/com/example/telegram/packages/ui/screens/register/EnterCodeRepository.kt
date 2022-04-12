package com.example.telegram.packages.ui.screens.register

import androidx.lifecycle.MutableLiveData
import com.example.telegram.database.*
import com.example.telegram.utilits.AppValueEventListener
import com.example.telegram.utilits.restartActivity
import com.example.telegram.utilits.showToast
import com.google.firebase.auth.PhoneAuthProvider

interface EnterCodeRepository {
    fun enterCode(code:String , phoneNumber: String,  id: String):MutableLiveData<String>

}
/* Функция проверяет код, если все нормально, производит создания информации о пользователе в базе данных.*/
class EnterCodeRepositoryImpl():EnterCodeRepository{
   private var result=MutableLiveData<String>()
    override fun enterCode(code:String , phoneNumber: String,  id: String): MutableLiveData<String> {
        val credential = PhoneAuthProvider.getCredential(id, code)

        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid)
                    .addListenerForSingleValueEvent(AppValueEventListener {

                        if (!it.hasChild(CHILD_USERNAME)) {
                            dateMap[CHILD_USERNAME] = uid
                        }

                        REF_DATABASE_ROOT.child(
                            NODE_PHONES
                        ).child(phoneNumber).setValue(uid)
                            .addOnFailureListener { result.value="${it.message}"}
                            .addOnSuccessListener {
                                REF_DATABASE_ROOT.child(
                                    NODE_USERS
                                ).child(uid).updateChildren(dateMap)
                                    .addOnSuccessListener {
                                        showToast("Добро пожаловать")
                                        restartActivity()
                                    }
                                    .addOnFailureListener { result.value="${it.message}" }
                            }
                    })


            } else result.value= "${task.exception?.message}"
        }

   return result }}
