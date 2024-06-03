package com.archx.home.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.archx.home.model.DefaultDeviceItems
import com.archx.home.model.DeviceItem
import com.archx.home.ui.screen.ErrorScreen
import com.archx.home.ui.screen.LoadingScreen
import com.archx.home.ui.theme.MyApplicationTheme


@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    retryAction: () -> Unit,
    onClickCard: (deviceId: String) -> Unit,
    onSwitchChanged: (deviceId: String, status: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when (uiState) {
        is HomeScreenUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is HomeScreenUiState.Success -> DeviceGrid(
            items = uiState.devices,
            onClickCard = onClickCard,
            onSwitchChanged = onSwitchChanged
        )

        is HomeScreenUiState.SuccessChangeStatus ->
            Toast.makeText(context, "Turn off device successfully", Toast.LENGTH_SHORT).show()

        else -> ErrorScreen(retryAction, modifier)
    }
}


@Composable
fun DeviceGrid(
    items: List<DeviceItem>,
    onClickCard: (deviceId: String) -> Unit,
    onSwitchChanged: (deviceId: String, status: String) -> Unit
) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(items) { device ->
                DeviceCard(
                    item = device,
                    onClickCard = onClickCard,
                    onSwitchChanged = onSwitchChanged
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(
    item: DeviceItem,
    onClickCard: (deviceId: String) -> Unit,
    onSwitchChanged: (deviceId: String, status: String) -> Unit
) {
    var isChecked by remember { mutableStateOf(item.enabled) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClickCard(item.id) },
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image aligned to the left
                Image(
                    painter = painterResource(id = item.getIconId()),
                    contentDescription = "Category Image",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        onSwitchChanged(item.id, isChecked.toString())
                    },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = item.deviceName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.place,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MyApplicationTheme {
        DeviceGrid(
            items = DefaultDeviceItems(),
            onClickCard = {},
            onSwitchChanged = { deviceId, status -> {} })
    }
}

