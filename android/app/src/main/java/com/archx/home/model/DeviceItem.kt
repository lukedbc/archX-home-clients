package com.archx.home.model

data class DeviceItem(
    val id: String,
    val category: String,
    val deviceName: String,
    val deviceFactoryName: String,
    val place: String,
//    val valueHightLight: String,
    val enabled: Boolean
)


fun DefaultDeviceItems(): List<DeviceItem> {
    return listOf(
        DeviceItem(
            id = "1",
            category = "Smart Speaker",
            deviceName = "Amazon Echo",
            deviceFactoryName = "Amazon",
            place = "Living room",
            enabled = true,
        ),
        DeviceItem(
            id = "2",
            category = "Smart Thermostat",
            deviceName = "Nest Thermostat",
            deviceFactoryName = "Google",
            place = "Living room",
            enabled = false
        ),
        DeviceItem(
            id = "3",
            category = "Smart TV",
            deviceName = "Samsung QLED TV",
            deviceFactoryName = "Samsung",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "4",
            category = "Smart Lock",
            deviceName = "August Smart Lock Pro",
            deviceFactoryName = "August",
            place = "Living room",
            enabled = true
        ),
        DeviceItem(
            id = "5",
            category = "Smart Bulb",
            deviceName = "Philips Hue",
            deviceFactoryName = "Philips",
            place = "Living room",
            enabled = false
        )
    )
}