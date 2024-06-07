package com.archx.home.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.archx.home.ui.screen.add_device.AddDeviceForm
import com.archx.home.ui.screen.add_device.AddDeviceViewModel
import com.archx.home.ui.screen.device_detail.DeviceDetailScreen
import com.archx.home.ui.screen.device_detail.DeviceDetailViewModel
import com.archx.home.ui.screen.home.HomeScreen
import com.archx.home.ui.screen.home.HomeScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArchHomeXScreen(modifier: Modifier) {
    val homeTab = TabBarItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    val addTab = TabBarItem(
        title = "Add",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add
    )

    val notification = TabBarItem(
        title = "Notification",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        badgeAmount = 7
    )

    val tabBarItems = listOf(homeTab, addTab, notification)
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            TabView(tabBarItems, navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainScreenNavigationConfigurations(navController = navController, items = tabBarItems)
        }
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    items: List<TabBarItem>,
) {
    val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory)
    val deviceDetailViewModel: DeviceDetailViewModel =
        viewModel(factory = DeviceDetailViewModel.Factory)

    val addDeviceViewModel: AddDeviceViewModel = viewModel(factory = AddDeviceViewModel.Factory)

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = items[0].title) {
        composable(items[0].title) {
            LaunchedEffect(Unit) {
                homeScreenViewModel.getDevices()
            }
            HomeScreen(
                uiState = homeScreenViewModel.uiState,
                retryAction = homeScreenViewModel::getDevices,
                modifier = Modifier.fillMaxSize(),
                onClickCard = { deviceId -> navController.navigate("deviceDetail/$deviceId") },
                onSwitchChanged = homeScreenViewModel::changeStatus
            )
        }
        composable(items[1].title) {
            AddDeviceForm(
                viewModel = addDeviceViewModel,
                onDeviceAdded = {
                    addDeviceViewModel.createDevice()
                    navController.navigate("Home")
                    Toast.makeText(context, "Added device successfully", Toast.LENGTH_SHORT)
                        .show()
                    addDeviceViewModel.reset()
                }
            )
        }
        composable(items[2].title) {
            Text(text = "Notification")
        }
        composable(
            "deviceDetail/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            LaunchedEffect(deviceId) {
                deviceDetailViewModel.getDevice(deviceId)
            }
            DeviceDetailScreen(
                uiState = deviceDetailViewModel.uiState,
                retryAction = { deviceDetailViewModel.getDevice(deviceId) },
                onBack = { navController.popBackStack() },
                onChangedStatus = { deviceId, status ->
                    deviceDetailViewModel.changeStatus(
                        deviceId,
                        status
                    )
                    navController.popBackStack()
                    Toast.makeText(context, "Turn off device successfully", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}