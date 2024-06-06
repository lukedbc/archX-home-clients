package com.archx.home.ui.screen.add_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.archx.home.ArchHomeXApplication
import com.archx.home.data.DeviceRepository
import com.archx.home.model.DeviceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DeviceFormState(
    val category: String = "",
    val deviceName: String = "",
    val deviceFactoryName: String = "",
    val place: String = "",
    val metadata: Map<String, String> = emptyMap()
)

class AddDeviceViewModel(private val repository: DeviceRepository) : ViewModel() {
    private val _formState = MutableStateFlow(DeviceFormState())
    val formState: StateFlow<DeviceFormState> = _formState

    fun updateCategory(category: String) {
        _formState.value = _formState.value.copy(category = category)
    }

    fun updateDeviceName(deviceName: String) {
        _formState.value = _formState.value.copy(deviceName = deviceName)
    }

    fun updateDeviceFactoryName(deviceFactoryName: String) {
        _formState.value = _formState.value.copy(deviceFactoryName = deviceFactoryName)
    }

    fun updatePlace(place: String) {
        _formState.value = _formState.value.copy(place = place)
    }

    fun addMetadata(key: String, value: String) {
        val updatedMetadata = _formState.value.metadata.toMutableMap()
        updatedMetadata[key] = value
        _formState.value = _formState.value.copy(metadata = updatedMetadata)
    }

    fun createDevice() {
        viewModelScope.launch {
            val formState = _formState.value
            val newDevice = DeviceItem(
                category = formState.category,
                deviceName = formState.deviceName,
                deviceFactoryName = formState.deviceFactoryName,
                place = formState.place,
                enabled = true,
                metadata = formState.metadata
            )
            // add database
            repository.addDevice(newDevice)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as ArchHomeXApplication)

                AddDeviceViewModel(application.container.deviceRepository)
            }
        }
    }
}
