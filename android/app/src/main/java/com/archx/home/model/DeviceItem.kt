package com.archx.home.model

import com.archx.home.R

data class DeviceItem(
    val id: String,
    val category: String,
    val deviceName: String,
    val deviceFactoryName: String,
    val place: String,
    val valueHighlight: String = "",
    val enabled: Boolean
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

    fun getMetaData(): Boolean {
        return false
    }
}
fun DefaultDeviceItems(): List<DeviceItem> {
    return listOf(
        DeviceItem(
            id = "1",
            category = "speaker",
            deviceName = "Amazon Echo",
            deviceFactoryName = "Amazon",
            place = "Living room",
            enabled = true,
        ),
        DeviceItem(
            id = "2",
            category = "thermostat",
            deviceName = "Nest Thermostat",
            deviceFactoryName = "Google",
            place = "Living room",
            enabled = true,
        ),
        DeviceItem(
            id = "3",
            category = "tv",
            deviceName = "Samsung QLED TV",
            deviceFactoryName = "Samsung",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "4",
            category = "lock",
            deviceName = "August Smart Lock Pro",
            deviceFactoryName = "August",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "5",
            category = "light",
            deviceName = "Philips Hue",
            deviceFactoryName = "Philips",
            place = "Living room",
            enabled = false
        ),
        DeviceItem(
            id = "6",
            category = "camera",
            deviceName = "Arlo Pro 3",
            deviceFactoryName = "Arlo",
            place = "Front door",
            enabled = true
        ),
        DeviceItem(
            id = "7",
            category = "vacuum",
            deviceName = "Roomba i7+",
            deviceFactoryName = "iRobot",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "8",
            category = "light",
            deviceName = "LIFX Smart Bulb",
            deviceFactoryName = "LIFX",
            place = "Bedroom",
            enabled = true
        ),
        DeviceItem(
            id = "9",
            category = "speaker",
            deviceName = "Google Home",
            deviceFactoryName = "Google",
            place = "Kitchen",
            enabled = true
        ),
        DeviceItem(
            id = "10",
            category = "doorbell",
            deviceName = "Ring Video Doorbell",
            deviceFactoryName = "Ring",
            place = "Front door",
            enabled = true
        ),
        DeviceItem(
            id = "11",
            category = "light",
            deviceName = "Nanoleaf Light Panels",
            deviceFactoryName = "Nanoleaf",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "12",
            category = "speaker",
            deviceName = "Sonos One",
            deviceFactoryName = "Sonos",
            place = "Bedroom",
            enabled = true
        )
    )
}
