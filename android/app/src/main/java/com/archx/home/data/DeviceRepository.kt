package com.archx.home.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.archx.home.data.remote.DeviceApi
import com.archx.home.model.ChangeStatusObject
import com.archx.home.model.DeviceItem

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDevice(device: DeviceItem)

    @Query("SELECT * FROM device")
    suspend fun getDevices(): List<DeviceItem>

    @Query("SELECT * FROM device WHERE id = :id")
    suspend fun getDevice(id: String): DeviceItem

    @Query("UPDATE device SET enabled = :status WHERE id = :id")
    suspend fun changeStatus(id: String, status: Boolean)
}

interface DeviceRepository {
    suspend fun getDevices(): List<DeviceItem>

    suspend fun getDevice(id: String): DeviceItem

    suspend fun changeStatus(deviceId: String, status: String): Boolean

    suspend fun addDevice(item: DeviceItem): Boolean
}

class NetworkDeviceRepository(private val apiService: DeviceApi): DeviceRepository {
    override suspend fun getDevices(): List<DeviceItem> {
        return apiService.retrofitService.getDevices()
    }

    override suspend fun getDevice(id: String): DeviceItem {
        return apiService.retrofitService.getDevice(id)
    }

    override suspend fun changeStatus(deviceId: String, status: String): Boolean {
        apiService.retrofitService.changeStatus(deviceId, ChangeStatusObject(status.toBoolean()))
        return true
    }

    override suspend fun addDevice(item: DeviceItem): Boolean {
        apiService.retrofitService.addDevice(item)
        return true
    }

}

class InternalDeviceRepository(private val appDatabase: AppDatabase) : DeviceRepository {

    private val deviceDao = appDatabase.deviceDao()
    override suspend fun getDevices(): List<DeviceItem> {
        return deviceDao.getDevices()
    }

    override suspend fun getDevice(id: String): DeviceItem {
        return deviceDao.getDevice(id)
    }

    override suspend fun changeStatus(deviceId: String, status: String): Boolean {
        deviceDao.changeStatus(deviceId, status.toBoolean())
        return true
    }

    override suspend fun addDevice(item: DeviceItem): Boolean {
        deviceDao.addDevice(item)
        return true
    }

}