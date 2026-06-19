package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataUsageScreen(onBackClick: () -> Unit) {
    var shareDiagnostics by remember { mutableStateOf(true) }
    var personalAds by remember { mutableStateOf(false) }
    var cloudStorage by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Data Usage") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.BarChart, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Cloud Storage Used", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("1.2 GB of 5 GB", style = MaterialTheme.typography.bodyMedium)
                        LinearProgressIndicator(
                            progress = { 0.24f },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        )
                    }
                }
            }

            Text("Data Settings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            DataToggleItem(
                title = "Share Diagnostic Data",
                subtitle = "Help us improve by sharing app performance and crash reports.",
                checked = shareDiagnostics,
                onCheckedChange = { shareDiagnostics = it }
            )

            DataToggleItem(
                title = "Cloud History Storage",
                subtitle = "Save your device activity and camera footage in our secure cloud.",
                checked = cloudStorage,
                onCheckedChange = { cloudStorage = it }
            )

            DataToggleItem(
                title = "Personalized Recommendations",
                subtitle = "Receive suggestions based on your smart home usage patterns.",
                checked = personalAds,
                onCheckedChange = { personalAds = it }
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "We prioritize your privacy. Your data is encrypted and only used to improve your smart home experience.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun DataToggleItem(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
fun DataUsageScreenPreview() {
    SmartHomeDesignTheme {
        DataUsageScreen(onBackClick = {})
    }
}
