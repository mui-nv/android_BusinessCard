package com.muinv.businesscard.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muinv.businesscard.repository.InformationRepository
import com.muinv.businesscard.repository.UserRepository
import com.muinv.businesscard.scene.login.LoginViewModel
import com.muinv.businesscard.scene.main.create.CreateViewModel
import com.muinv.businesscard.scene.main.edit.EditViewModel
import com.muinv.businesscard.scene.main.search.SearchViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val informationRepository: InformationRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(userRepository)
                isAssignableFrom(CreateViewModel::class.java) ->
                    CreateViewModel(informationRepository)
                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(informationRepository)
                isAssignableFrom(EditViewModel::class.java) ->
                    EditViewModel(informationRepository)
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
                    Injection.provideUserRepository(application.applicationContext),
                    Injection.provideInformationRepository(application.applicationContext)
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}