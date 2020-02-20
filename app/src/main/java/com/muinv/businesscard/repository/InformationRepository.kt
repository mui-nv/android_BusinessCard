package com.muinv.businesscard.repository

import android.util.Base64
import com.muinv.businesscard.data.local.information.InformationDao
import com.muinv.businesscard.data.local.user.UserDao
import com.muinv.businesscard.data.remote.ApiService
import com.muinv.businesscard.data.remote.data.Information
import com.muinv.businesscard.data.remote.param.BaseParam
import com.muinv.businesscard.data.remote.param.DeleteParam
import com.muinv.businesscard.data.remote.param.ImageParam
import com.muinv.businesscard.data.remote.response.ImageResponse
import com.muinv.businesscard.data.remote.response.SuccessResponse
import com.muinv.businesscard.repository.mapper.InformationMapper
import com.google.gson.*
import io.reactivex.Observable
import java.lang.reflect.Type

class InformationRepository(
    apiService: ApiService, val userDao: UserDao,
    val informationDao: InformationDao,
    val informationMapper: InformationMapper
) : BaseRepository(apiService) {

    fun allData(): Observable<List<Information>> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            var param = BaseParam(it)
            return@flatMap requestApiList<BaseParam, Information>(
                ApiAddress.GET_ALL_INFORMATION,
                param,
                {
                    informationDao.insertInformations(it.map {
                        informationMapper.dataToObject(it)
                    })
                }
            )
        }
    }

    fun create(information: Information): Observable<Information> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            information.userID = it

            return@flatMap requestApi<Information, Information>(
                ApiAddress.CREATE_INFORMATION,
                information
            )
        }
    }

    fun update(information: Information): Observable<SuccessResponse> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            information.userID = it
            information.image_base64 = null

            val gson = GsonBuilder().registerTypeHierarchyAdapter(
                ByteArray::class.java,
                ByteArrayToBase64TypeAdapter()
            ).create()

            return@flatMap requestApi<Information, SuccessResponse>(
                ApiAddress.UPDATE_INFORMATION, information, gson
            )
        }
    }

    fun getImage(image: String): Observable<ImageResponse> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            var imageParam = ImageParam(image)

            return@flatMap requestApi<ImageParam, ImageResponse>(
                ApiAddress.GET_IMAGE, imageParam
            )
        }
    }

    fun delete(id: Int): Observable<SuccessResponse> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            var param = DeleteParam(id, it)

            return@flatMap requestApi<DeleteParam, SuccessResponse>(
                ApiAddress.DELETE_INFORMATION, param
            )
        }
    }

    companion object {

        private var INSTANCE: InformationRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            informationDao: InformationDao,
            informationMapper: InformationMapper
        ) =
            INSTANCE ?: synchronized(UserRepository::class.java) {
                INSTANCE ?: InformationRepository(
                    apiService,
                    userDao,
                    informationDao,
                    informationMapper
                )
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private class ByteArrayToBase64TypeAdapter : JsonSerializer<ByteArray>,
        JsonDeserializer<ByteArray> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): ByteArray {
            return Base64.decode(json.asString, Base64.NO_WRAP)
        }

        override fun serialize(
            src: ByteArray,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP))
        }
    }
}