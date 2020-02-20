package com.muinv.businesscard.repository.mapper

import com.muinv.businesscard.data.local.`object`.UserObject
import com.muinv.businesscard.data.remote.data.User

class UserMapper {
    fun fromDataToObject(user: User): UserObject {
        return UserObject(user.id, user.user, user.password)
    }
}