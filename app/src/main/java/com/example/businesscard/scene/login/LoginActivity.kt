package com.example.businesscard.scene.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.R
import com.example.businesscard.data.local.ToDoDatabase
import com.example.businesscard.data.remote.ApiConfig
import com.example.businesscard.data.remote.param.LoginParam
import com.example.businesscard.repository.UserRepository
import com.example.businesscard.repository.mapper.UserMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userRepository = UserRepository(
            ApiConfig().createApiService(),
            ToDoDatabase.getInstance(this).userDao(), UserMapper()
        )

        var loginParam = LoginParam()
        loginParam.user = "muinv"
        loginParam.password = "123"
        disposable.add(
            userRepository.userLogin(loginParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    Log.i("TAG1", value.user)
                }, { error ->
                    Log.i("TAG1", "error" + error.toString())
                })
        )
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}
