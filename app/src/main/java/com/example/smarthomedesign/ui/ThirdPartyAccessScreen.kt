package com.example.smarthomedesign.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdPartyAccessScreen(onBackClick: () -> Unit) {
    val apps = listOf(
        ThirdPartyApp("Google Home", "Full Access", "Linked on Oct 12, 2023"),
        ThirdPartyApp("Amazon Alexa", "Partial Access", "Linked on Oct 15, 2023"),
        ThirdPartyApp("Apple HomeKit", "Full Access", "Linked on Nov 02, 2023"),
        ThirdPartyApp("Samsung SmartThings", "Device Management", "Linked on Dec 20, 2023")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Third-party Access") },
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
        ) {
            Text(
                text = "Apps and services with access to your home data and devices.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(apps) { app ->
                    AppAccessItem(app)
                }
            }
        }
    }
}

data class ThirdPartyApp(val name: String, val accessLevel: String, val linkedDate: String)

@Composable
fun AppAccessItem(app: ThirdPartyApp) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = app.accessLevel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                Text(text = app.linkedDate, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Access", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdPartyAccessScreenPreview() {
    SmartHomeDesignTheme {
        ThirdPartyAccessScreen(onBackClick = {})
    }
}
