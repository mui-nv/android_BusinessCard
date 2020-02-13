package com.example.businesscard.scene.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.R
import com.example.businesscard.data.remote.ApiConfig
import com.example.businesscard.data.remote.param.LoginParam
import com.example.businesscard.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {
    val userRepository = UserRepository(ApiConfig().createApiService())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var loginParam = LoginParam()
        loginParam.user = "muinv"
        loginParam.password = "123"
        userRepository.userLogin(loginParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value ->
                Log.i("TAG1", value.user)
            }, { error ->
                Log.i("TAG1", "error" + error.toString())
            })
    }
}
