package com.archx.home.data.remote

import com.archx.home.model.ChangeStatusObject
import com.archx.home.model.DeviceItem
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8000"

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DeviceApiService {
    @GET("devices")
    suspend fun getDevices(): List<DeviceItem>

    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") id: String): DeviceItem

    @PATCH("devices/{id}")
    suspend fun changeStatus(@Path("id") id: String, @Body changeStatusObject: ChangeStatusObject)

    @POST("devices")
    suspend fun addDevice(@Body device:DeviceItem)
}

object DeviceApi {
    val retrofitService: DeviceApiService by lazy { retrofit.create(DeviceApiService::class.java) }
}