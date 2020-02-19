package com.example.businesscard.repository.mapper

import com.example.businesscard.data.local.`object`.InformationObject
import com.example.businesscard.data.remote.data.Information

class InformationMapper {
    fun dataToObject(data: Information): InformationObject {
        return InformationObject(
            data.id,
            data.userID,
            data.name1,
            data.name2,
            data.company,
            data.department,
            data.postal,
            data.address1,
            data.address2,
            data.latitude,
            data.longitude,
            data.image
        )
    }

    fun objectToData(data: InformationObject): Information {
        return Information(
            data.id,
            data.userID,
            data.name1,
            data.name2,
            data.company,
            data.deparment,
            data.postal,
            data.address1,
            data.address2,
            data.latitude,
            data.longitude,
            data.image,
            null
        )
    }
}