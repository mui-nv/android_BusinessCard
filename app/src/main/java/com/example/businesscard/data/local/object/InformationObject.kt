package com.example.businesscard.data.local.`object`

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "informations")
class InformationObject @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "userID") var userID: Int? = 0,
    @ColumnInfo(name = "name1") var name1: String? = "",
    @ColumnInfo(name = "name2") var name2: String? = "",
    @ColumnInfo(name = "company") var company: String? = "",
    @ColumnInfo(name = "postal") var postal: String? = "",
    @ColumnInfo(name = "deparment") var deparment: String? = "",
    @ColumnInfo(name = "address1") var address1: String? = "",
    @ColumnInfo(name = "address2") var address2: String? = "",
    @ColumnInfo(name = "latitude") var latitude: Double? = 0.0,
    @ColumnInfo(name = "longitude") var longitude: Double? = 0.0,
    @ColumnInfo(name = "image") var image: String? = ""
)