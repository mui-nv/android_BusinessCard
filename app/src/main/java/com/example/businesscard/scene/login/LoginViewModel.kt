package com.example.businesscard.scene.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.businesscard.data.remote.param.LoginParam
import com.example.businesscard.repository.UserRepository
import com.example.businesscard.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(val userRepository: UserRepository) : ViewModel() {
    val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName
    val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    fun userLogin() {
        var loginParam = LoginParam()
        loginParam.user = userName.value.toString()
        loginParam.password = password.value.toString()
        userRepository.userLogin(loginParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _errorMessage.value = Event("Success")
            }, { error ->
                _errorMessage.value = Event(error.toString())
                error.printStackTrace()
            })
    }
}