package com.archx.home.ui.screen.device_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.archx.home.ArchHomeXApplication
import com.archx.home.data.DeviceRepository
import com.archx.home.model.DefaultDeviceItems
import com.archx.home.model.DeviceItem
import kotlinx.coroutines.launch


sealed interface DeviceDetailUiState {
    data class Success(val device: DeviceItem) : DeviceDetailUiState
    object Error : DeviceDetailUiState
    object Loading : DeviceDetailUiState
}

class DeviceDetailViewModel(private val repository: DeviceRepository) : ViewModel() {
    var uiState: DeviceDetailUiState by mutableStateOf(DeviceDetailUiState.Loading)
        private set

    fun getDevice(id: String) {
        viewModelScope.launch {
            uiState = DeviceDetailUiState.Loading
            uiState = try {
                val device = repository.getDevice(id)
                DeviceDetailUiState.Success(device)
            } catch (e: Exception) {
                DeviceDetailUiState.Error
            }
        }
    }

    fun changeStatus(deviceId: String, status: String) {
        viewModelScope.launch {
            repository.changeStatus(deviceId, status)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as ArchHomeXApplication)

                DeviceDetailViewModel(application.container.deviceRepository)
            }
        }
    }
}
