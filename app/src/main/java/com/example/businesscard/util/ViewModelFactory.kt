package com.example.businesscard.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.businesscard.repository.UserRepository
import com.example.businesscard.scene.login.LoginViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(userRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideUserRepository(application.applicationContext)
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}