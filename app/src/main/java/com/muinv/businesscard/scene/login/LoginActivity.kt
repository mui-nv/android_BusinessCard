package com.muinv.businesscard.scene.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.muinv.businesscard.R
import com.muinv.businesscard.databinding.ActivityLoginBinding
import com.muinv.businesscard.scene.main.MainActivity
import com.muinv.businesscard.util.obtainViewModel
import com.muinv.businesscard.util.showAlertMessage
import io.reactivex.disposables.CompositeDisposable


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
