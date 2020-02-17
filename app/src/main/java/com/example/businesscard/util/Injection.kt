package com.example.businesscard.util

import android.content.Context
import com.example.businesscard.data.local.ToDoDatabase
import com.example.businesscard.data.remote.ApiConfig
import com.example.businesscard.repository.InformationRepository
import com.example.businesscard.repository.UserRepository
import com.example.businesscard.repository.mapper.InformationMapper
import com.example.businesscard.repository.mapper.UserMapper

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
            database.userDao(), InformationMapper()
        )
    }
}