package com.archx.home.ui.screen

import android.R.attr.maxLines
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.archx.home.R
import com.archx.home.model.DefaultDeviceItems
import com.archx.home.model.DeviceItem
import com.archx.home.ui.theme.MyApplicationTheme


@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search...") },
            shape = RoundedCornerShape(20), // Round corners
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(DefaultDeviceItems()) { // Adjust the item count as needed
                    device ->
                DeviceCard(device)
            }
        }
    }
}

@Composable
fun DeviceCard(item: DeviceItem) {
    var isChecked by remember { mutableStateOf(item.enabled) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image aligned to the left
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your image resource
                    contentDescription = "Sample Image",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
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
        HomeScreen()
    }
}
