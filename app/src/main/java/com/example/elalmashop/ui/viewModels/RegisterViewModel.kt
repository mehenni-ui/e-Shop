package com.example.elalmashop.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elalmashop.ui.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterViewModel: ViewModel() {

    private val _taskAuthSignUp = MutableLiveData<Resource<Task<AuthResult>>>()
    private var taskAUthSignUp: LiveData<Resource<Task<AuthResult>>> = _taskAuthSignUp

    private val _taskAuthLogIn = MutableLiveData<Resource<Task<AuthResult>>>()
    private var taskAUthLogIn: LiveData<Resource<Task<AuthResult>>> = _taskAuthLogIn

    private var auth : FirebaseAuth = Firebase.auth

    private fun userSignUp(email: String, password: String, confirmPassword: String){

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            _taskAuthSignUp.postValue(Resource.Error("Check your info"))
        }else if (password != confirmPassword){
            _taskAuthSignUp.postValue(Resource.Error("please enter the same password"))
        }else if (password.length < 6){
            _taskAuthSignUp.postValue(Resource.Error("password must contain 6 characters or more"))
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    _taskAuthSignUp.postValue(Resource.Success(task))
                }else{
                    Log.w("error_signup", task.exception)
                    _taskAuthSignUp.postValue(Resource.Error(task.toString()))
                }
            }
    }

    private fun userLogIn(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()) {
            _taskAuthLogIn.postValue(Resource.Error("Check your info"))
        }else if (password.length < 6){
            _taskAuthLogIn.postValue(Resource.Error("password must contain 6 characters or more"))
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    _taskAuthLogIn.postValue(Resource.Success(task))
                }
                else{
                    Log.w("error_login", task.exception)
                    _taskAuthLogIn.postValue(Resource.Error(task.toString()))
                }
            }
    }

    fun observeSignUp(email: String, password: String, confirmPassword: String) : LiveData<Resource<Task<AuthResult>>>{
        userSignUp(email, password, confirmPassword)
        return taskAUthSignUp
    }

    fun observeLogin(email: String, password: String): LiveData<Resource<Task<AuthResult>>>{
        userLogIn(email, password)
        return taskAUthLogIn
    }



}