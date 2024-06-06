package com.archx.home.ui.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.archx.home.ArchHomeXApplication
import com.archx.home.data.DeviceRepository
import com.archx.home.data.InternalDeviceRepository
import com.archx.home.model.DefaultDeviceItems
import com.archx.home.model.DeviceItem
import kotlinx.coroutines.launch

sealed interface HomeScreenUiState {
    data class Success(val devices: List<DeviceItem>) : HomeScreenUiState
    data class SuccessChangeStatus(val device: DeviceItem) : HomeScreenUiState
    object Error : HomeScreenUiState
    object Loading : HomeScreenUiState
}

class HomeScreenViewModel(private val repository: DeviceRepository) : ViewModel() {
    var uiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.Loading)
        private set

    fun getDevices() {
        viewModelScope.launch {
            uiState = HomeScreenUiState.Loading
            uiState = try {
                HomeScreenUiState.Success(repository.getDevices())
            } catch (e: Exception) {
                HomeScreenUiState.Error
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

                HomeScreenViewModel(application.container.deviceRepository)

            }
        }
    }
}