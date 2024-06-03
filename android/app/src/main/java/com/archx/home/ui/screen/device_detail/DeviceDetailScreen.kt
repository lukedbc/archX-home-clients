package com.archx.home.ui.screen.device_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.archx.home.R
import com.archx.home.model.DeviceItem
import com.archx.home.ui.screen.ErrorScreen
import com.archx.home.ui.screen.LoadingScreen
import com.archx.home.ui.theme.MyApplicationTheme

@Composable
fun DeviceDetailScreen(
    uiState: DeviceDetailUiState,
    retryAction: () -> Unit,
    onBack: () -> Unit,
    onChangedStatus: (deviceId: String, status: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is DeviceDetailUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is DeviceDetailUiState.Success -> DeviceDetail(
            deviceItem = uiState.device,
            onBack = onBack,
            onChangedStatus = onChangedStatus
        )

        else -> ErrorScreen(retryAction, modifier)
    }
}

@Composable
fun getEnabledColor(deviceItem: DeviceItem?): Color {
    if (deviceItem?.enabled == true)
        return MaterialTheme.colorScheme.primary
    else
        return Color.Gray
}

@Composable
fun DeviceDetail(
    deviceItem: DeviceItem?,
    onBack: () -> Unit,
    onChangedStatus: (deviceId: String, status: String) -> Unit
) {
    deviceItem?.let {
        var isChecked by remember { mutableStateOf(deviceItem.enabled) }
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
            )
        }
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = deviceItem.getIconId()),
                contentDescription = "Category Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = deviceItem.deviceName,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            ) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.setting),
                            contentDescription = "Status image"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onChangedStatus(deviceItem.id, (!deviceItem.enabled).toString())
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getEnabledColor(deviceItem = deviceItem)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.shutdown),
                            contentDescription = "Status image"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Status image"
                        )
                    }
                }
            }
            val hasMetaData = deviceItem.getMetaData()
            if (hasMetaData) {
                val sampleData = mapOf(
                    "Name" to "John Doe",
                    "Age" to "30",
                    "Occupation" to "Software Engineer",
                    "Country" to "USA"
                )
                KeyValueCard(keyValuePairs = sampleData)
            }
        }
    }
}


@Composable
fun KeyValueCard(keyValuePairs: Map<String, String>) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            keyValuePairs.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = key, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = value, modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKeyValueCard() {
    val sampleData = mapOf(
        "Name" to "John Doe", "Age" to "30", "Occupation" to "Software Engineer", "Country" to "USA"
    )
    KeyValueCard(sampleData)
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyApplicationTheme {
        val sample = DeviceItem(
            id = "1",
            category = "speaker",
            deviceName = "Amazon Echo",
            deviceFactoryName = "Amazon",
            place = "Living room",
            enabled = true,
        )
        DeviceDetail(sample, onBack = {}, onChangedStatus = { deviceId, status -> {} })
    }
}
