/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.muinv.businesscard.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muinv.businesscard.data.local.`object`.InformationObject
import com.muinv.businesscard.data.local.`object`.UserObject
import com.muinv.businesscard.data.local.information.InformationDao
import com.muinv.businesscard.data.local.user.UserDao

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = arrayOf(UserObject::class, InformationObject::class), version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun informationDao(): InformationDao

    companion object {

        private var INSTANCE: ToDoDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): ToDoDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ToDoDatabase::class.java, "BusinessCard.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }

}