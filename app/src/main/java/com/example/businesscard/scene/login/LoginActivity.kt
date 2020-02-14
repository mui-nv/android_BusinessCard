package com.example.businesscard.scene.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.businesscard.R
import com.example.businesscard.data.local.ToDoDatabase
import com.example.businesscard.data.remote.ApiConfig
import com.example.businesscard.data.remote.param.LoginParam
import com.example.businesscard.databinding.ActivityLoginBinding
import com.example.businesscard.repository.UserRepository
import com.example.businesscard.repository.mapper.UserMapper
import com.example.businesscard.scene.main.MainActivity
import com.example.businesscard.util.obtainViewModel
import com.example.businesscard.util.showAlertMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {
    val disposable = CompositeDisposable()
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loginBinding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        loginViewModel = obtainViewModel()
            .apply {
                errorMessage.observe(this@LoginActivity, Observer {
                    it.getContentIfNotHandled()?.let {
                        if (it == "Success") {
                            moveToMain()
                        } else {
                            showAlertMessage(it)
                        }
                    }
                })
            }

        loginBinding.apply {
            viewModel = loginViewModel
            btnLogin.setOnClickListener {
                (viewModel as LoginViewModel).userLogin()
            }
        }
    }

    fun moveToMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)
}
