package com.example.smarthomedesign.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.sp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme
import kotlinx.coroutines.delay

@Composable
fun CallSupportScreen(onEndCall: () -> Unit) {
    var seconds by remember { mutableIntStateOf(0) }
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            seconds++
        }
    }

    val timeString = remember(seconds) {
        val mins = seconds / 60
        val secs = seconds % 60
        "%02d:%02d".format(mins, secs)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1A1C1E) // Dark background for call screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(48.dp))
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.SupportAgent,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Smart Home Support",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "+91 78753 11455",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = timeString,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CallControlButton(
                        icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        label = "Mute",
                        isActive = isMuted,
                        onClick = { isMuted = !isMuted }
                    )
                    CallControlButton(
                        icon = Icons.Default.Dialpad,
                        label = "Keypad",
                        onClick = {}
                    )
                    CallControlButton(
                        icon = Icons.Default.VolumeUp,
                        label = "Speaker",
                        isActive = isSpeakerOn,
                        onClick = { isSpeakerOn = !isSpeakerOn }
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CallControlButton(
                        icon = Icons.Default.Add,
                        label = "Add call",
                        onClick = {}
                    )
                    CallControlButton(
                        icon = Icons.Default.VideoCall,
                        label = "Video",
                        onClick = {}
                    )
                    CallControlButton(
                        icon = Icons.Default.Bluetooth,
                        label = "Bluetooth",
                        onClick = {}
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // End Call Button
                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CallControlButton(
    icon: ImageVector,
    label: String,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(if (isActive) Color.White else Color.White.copy(alpha = 0.1f))
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = if (isActive) Color.Black else Color.White
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview
@Composable
fun CallSupportScreenPreview() {
    SmartHomeDesignTheme {
        CallSupportScreen(onEndCall = {})
    }
}
