package com.example.smarthomedesign.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiSetupScreen(onBackClick: () -> Unit) {
    var isScanning by remember { mutableStateOf(false) }
    var selectedWifi by remember { mutableStateOf<String?>(null) }
    var wifiPassword by remember { mutableStateOf("") }
    var connectionStatus by remember { mutableStateOf("IDLE") } // IDLE, CONNECTING, SUCCESS
    val scope = rememberCoroutineScope()

    val availableNetworks = listOf(
        WifiNetwork("SmartHome_5G", "Connected", 4, true),
        WifiNetwork("Airtel_Extreme", "Secure", 3, false),
        WifiNetwork("JioFiber_99", "Secure", 4, false),
        WifiNetwork("Guest_WiFi", "Open", 2, false),
        WifiNetwork("TP-Link_Work", "Secure", 1, false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WiFi Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        scope.launch {
                            isScanning = true
                            delay(2000)
                            isScanning = false
                        }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Scan")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isScanning) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Scanning for networks...")
                    }
                }
            } else {
                Text(
                    text = "Available Networks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(availableNetworks) { network ->
                        WifiRow(
                            network = network,
                            onClick = { selectedWifi = network.ssid }
                        )
                    }
                }
            }
        }

        if (selectedWifi != null) {
            ModalBottomSheet(
                onDismissRequest = { 
                    if (connectionStatus != "CONNECTING") selectedWifi = null 
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Connect to $selectedWifi",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    if (connectionStatus == "CONNECTING") {
                        CircularProgressIndicator(modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Establishing secure connection...")
                    } else if (connectionStatus == "SUCCESS") {
                        Icon(
                            Icons.Default.CheckCircle, 
                            contentDescription = null, 
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Connected successfully!")
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { 
                                selectedWifi = null
                                connectionStatus = "IDLE"
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Done")
                        }
                    } else {
                        OutlinedTextField(
                            value = wifiPassword,
                            onValueChange = { wifiPassword = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    connectionStatus = "CONNECTING"
                                    delay(2500)
                                    connectionStatus = "SUCCESS"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Connect")
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

data class WifiNetwork(val ssid: String, val status: String, val signal: Int, val isConnected: Boolean)

@Composable
fun WifiRow(network: WifiNetwork, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (network.isConnected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (network.isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (network.isConnected) Icons.Default.Wifi else Icons.Default.WifiLock,
                    contentDescription = null,
                    tint = if (network.isConnected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = network.ssid, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = network.status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                imageVector = when(network.signal) {
                    4 -> Icons.Default.SignalWifi4Bar
                    else -> Icons.Default.SignalWifiStatusbar4Bar
                },
                contentDescription = null,
                tint = if (network.isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WifiSetupScreenPreview() {
    SmartHomeDesignTheme {
        WifiSetupScreen(onBackClick = {})
    }
}
