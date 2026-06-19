package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLogScreen(onBackClick: () -> Unit) {
    val logs = listOf(
        ActivityLog("Main Door", "Unlocked via Fingerprint", "Today, 10:24 AM", Icons.Default.LockOpen),
        ActivityLog("Living Room Light", "Turned ON", "Today, 09:15 AM", Icons.Default.Lightbulb),
        ActivityLog("Kitchen Camera", "Motion Detected", "Today, 08:30 AM", Icons.Default.Videocam),
        ActivityLog("Smart Thermostat", "Temperature set to 22°C", "Yesterday, 11:45 PM", Icons.Default.Thermostat),
        ActivityLog("Garage Door", "Closed", "Yesterday, 06:20 PM", Icons.Default.Garage),
        ActivityLog("Main Door", "Locked", "Yesterday, 06:15 PM", Icons.Default.Lock)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activity Log") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(logs) { log ->
                LogItem(log)
            }
        }
    }
}

data class ActivityLog(val device: String, val action: String, val time: String, val icon: ImageVector)

@Composable
fun LogItem(log: ActivityLog) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = log.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = log.device, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = log.action, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = log.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityLogScreenPreview() {
    SmartHomeDesignTheme {
        ActivityLogScreen(onBackClick = {})
    }
}
