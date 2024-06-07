package com.archx.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.archx.home.R


data class ChangeStatusObject(
    val enabled: Boolean
)

@Entity(tableName = "device")
data class DeviceItem(
    @PrimaryKey val id: String = "0",
    val category: String,
    val deviceName: String,
    val deviceFactoryName: String,
    val place: String,
    val valueHighlight: String = "",
    val enabled: Boolean,
    val metadata: Map<String, String> = emptyMap()
) {
    fun getIconId(): Int {
        when (this.category.lowercase()) {
            "speaker" -> return R.drawable.home_speaker
            "tv" -> return R.drawable.tv
            "lock" -> return R.drawable.lock
            "ac" -> return R.drawable.air_conditioner
            "light" -> return R.drawable.light
            else -> return R.drawable.other
        }
    }

    fun hasMetaData(): Boolean {
        return !this.metadata.isEmpty()
    }
}
// For testing
fun DefaultDeviceItems(): List<DeviceItem> {
    return listOf(
        DeviceItem(
            category = "speaker",
            deviceName = "Amazon Echo",
            deviceFactoryName = "Amazon",
            place = "Living room",
            enabled = true,
        ),
        DeviceItem(
            category = "thermostat",
            deviceName = "Nest Thermostat",
            deviceFactoryName = "Google",
            place = "Living room",
            enabled = true,
        ),
        DeviceItem(
            category = "tv",
            deviceName = "Samsung QLED TV",
            deviceFactoryName = "Samsung",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            category = "lock",
            deviceName = "August Smart Lock Pro",
            deviceFactoryName = "August",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            category = "light",
            deviceName = "Philips Hue",
            deviceFactoryName = "Philips",
            place = "Living room",
            enabled = false
        ),
        DeviceItem(
            category = "camera",
            deviceName = "Arlo Pro 3",
            deviceFactoryName = "Arlo",
            place = "Front door",
            enabled = true
        ),
        DeviceItem(
            category = "vacuum",
            deviceName = "Roomba i7+",
            deviceFactoryName = "iRobot",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            category = "light",
            deviceName = "LIFX Smart Bulb",
            deviceFactoryName = "LIFX",
            place = "Bedroom",
            enabled = true
        ),
    )
}
