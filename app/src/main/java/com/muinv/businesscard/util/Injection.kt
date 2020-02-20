package com.muinv.businesscard.util

import android.content.Context
import com.muinv.businesscard.data.local.ToDoDatabase
import com.muinv.businesscard.data.remote.ApiConfig
import com.muinv.businesscard.repository.InformationRepository
import com.muinv.businesscard.repository.UserRepository
import com.muinv.businesscard.repository.mapper.InformationMapper
import com.muinv.businesscard.repository.mapper.UserMapper

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val database = ToDoDatabase.getInstance(context)
        val apiService = ApiConfig().createApiService()
        return UserRepository.getInstance(
            apiService,
            database.userDao(), UserMapper()
        )
    }

    fun provideInformationRepository(context: Context): InformationRepository {
        val database = ToDoDatabase.getInstance(context)
        val apiService = ApiConfig().createApiService()
        return InformationRepository.getInstance(
            apiService,
            database.userDao(), database.informationDao(), InformationMapper()
        )
    }
}