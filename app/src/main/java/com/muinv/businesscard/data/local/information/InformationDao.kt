package com.muinv.businesscard.data.local.information

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muinv.businesscard.data.local.`object`.InformationObject

@Dao
interface InformationDao {
    @Query("SELECT * FROM informations")
    fun getInformations(): List<InformationObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInformation(information: InformationObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInformations(information: List<InformationObject>)
}