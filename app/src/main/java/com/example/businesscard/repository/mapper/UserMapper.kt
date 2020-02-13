package com.example.businesscard.repository.mapper

import com.example.businesscard.data.local.`object`.UserObject
import com.example.businesscard.data.remote.data.User

class UserMapper {
    fun fromDataToObject(user: User): UserObject {
        return UserObject(user.id, user.user, user.password)
    }
}