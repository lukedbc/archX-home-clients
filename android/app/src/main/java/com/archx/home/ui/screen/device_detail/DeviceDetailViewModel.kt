package com.archx.home.ui.screen.device_detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.archx.home.ArchHomeXApplication
import com.archx.home.model.DefaultDeviceItems
import com.archx.home.model.DeviceItem
import com.archx.home.ui.screen.home.HomeScreenUiState
import kotlinx.coroutines.launch


sealed interface DeviceDetailUiState {
    data class Success(val device: DeviceItem?) : DeviceDetailUiState
    object Error : DeviceDetailUiState
    object Loading : DeviceDetailUiState
}

class DeviceDetailViewModel() : ViewModel() {
    var uiState: DeviceDetailUiState by mutableStateOf(DeviceDetailUiState.Loading)
        private set

    fun getDevice(id: String) {
        viewModelScope.launch {
            uiState = DeviceDetailUiState.Loading
            uiState = try {
                DeviceDetailUiState.Success(DefaultDeviceItems().firstOrNull { it.id == id })
            } catch (e: Exception) {
                DeviceDetailUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as ArchHomeXApplication)

                DeviceDetailViewModel()
            }
        }
    }
}
