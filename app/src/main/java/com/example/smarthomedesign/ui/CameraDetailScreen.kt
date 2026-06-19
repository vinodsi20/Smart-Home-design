package com.example.smarthomedesign.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraDetailScreen(
    cameraName: String = "Kitchen Camera",
    onBackClick: () -> Unit
) {
    var isRecording by remember { mutableStateOf(false) }
    var motionDetection by remember { mutableStateOf(true) }
    var nightVision by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cameraName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Live Feed Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Videocam, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    Text("LIVE FEED", color = Color.White, fontWeight = FontWeight.Bold)
                }
                
                // Overlay
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isRecording) Color.Red else Color.Green)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isRecording) "RECORDING" else "LIVE",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ControlButton(
                    icon = if (isRecording) Icons.Default.StopCircle else Icons.Default.FiberManualRecord,
                    label = if (isRecording) "Stop" else "Record",
                    onClick = { isRecording = !isRecording },
                    color = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
                ControlButton(icon = Icons.Default.Mic, label = "Talk", onClick = {})
                ControlButton(icon = Icons.Default.PhotoCamera, label = "Snapshot", onClick = {})
                ControlButton(icon = Icons.AutoMirrored.Filled.VolumeUp, label = "Listen", onClick = {})
            }

            HorizontalDivider()

            // Settings
            Text("Camera Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            CameraSettingToggle(
                title = "Motion Detection",
                description = "Get notified when movement is detected",
                checked = motionDetection,
                onCheckedChange = { motionDetection = it }
            )

            CameraSettingToggle(
                title = "Auto Night Vision",
                description = "Better visibility in low light",
                checked = nightVision,
                onCheckedChange = { nightVision = it }
            )

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Default.History, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Recent Clips")
            }
        }
    }
}

@Composable
fun ControlButton(icon: ImageVector, label: String, onClick: () -> Unit, color: Color = MaterialTheme.colorScheme.primary) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(28.dp))
        ) {
            Icon(icon, contentDescription = label, tint = color)
        }
        Text(label, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun CameraSettingToggle(title: String, description: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
fun CameraDetailScreenPreview() {
    SmartHomeDesignTheme {
        CameraDetailScreen(onBackClick = {})
    }
}
