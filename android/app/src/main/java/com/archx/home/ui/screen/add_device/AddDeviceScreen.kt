package com.archx.home.ui.screen.add_device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddDeviceForm(viewModel: AddDeviceViewModel, onDeviceAdded: () -> Unit) {
    val formState by viewModel.formState.collectAsState()

    var metadataKey by remember { mutableStateOf("") }
    var metadataValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = formState.category,
            onValueChange = { viewModel.updateCategory(it) },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.deviceName,
            onValueChange = { viewModel.updateDeviceName(it) },
            label = { Text("Device Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.deviceFactoryName,
            onValueChange = { viewModel.updateDeviceFactoryName(it) },
            label = { Text("Device Factory Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = formState.place,
            onValueChange = { viewModel.updatePlace(it) },
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = metadataKey,
                onValueChange = { metadataKey = it },
                label = { Text("Metadata Key") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = metadataValue,
                onValueChange = { metadataValue = it },
                label = { Text("Metadata Value") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (metadataKey.isNotEmpty() && metadataValue.isNotEmpty()) {
                    viewModel.addMetadata(metadataKey, metadataValue)
                    metadataKey = ""
                    metadataValue = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Metadata")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onDeviceAdded()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Device")
        }
    }
}