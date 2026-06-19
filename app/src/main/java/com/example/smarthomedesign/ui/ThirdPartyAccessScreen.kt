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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdPartyAccessScreen(onBackClick: () -> Unit) {
    var selectedApp by remember { mutableStateOf<ThirdPartyApp?>(null) }
    val sheetState = rememberModalBottomSheetState()

    val apps = remember {
        mutableStateListOf(
            ThirdPartyApp("Google Home", "Full Access • Controls 12 devices", "Linked on Oct 12, 2023", Color(0xFF4285F4)),
            ThirdPartyApp("Amazon Alexa", "Partial Access • Voice control only", "Linked on Oct 15, 2023", Color(0xFF00A8E1)),
            ThirdPartyApp("Apple HomeKit", "Full Access • Secure HomeKit Link", "Linked on Nov 02, 2023", Color(0xFF000000)),
            ThirdPartyApp("Samsung SmartThings", "Device Management • Monitoring", "Linked on Dec 20, 2023", Color(0xFF152647))
        )
    }

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
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Apps and services with access to your home data and devices. You can manage or revoke access at any time.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(apps) { app ->
                    AppAccessItem(
                        app = app,
                        onEditClick = { selectedApp = app },
                        onRemoveClick = { apps.remove(app) }
                    )
                }
            }
        }

        if (selectedApp != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedApp = null },
                sheetState = sheetState
            ) {
                AccessManagementSheet(
                    app = selectedApp!!,
                    onDismiss = { selectedApp = null }
                )
            }
        }
    }
}

data class ThirdPartyApp(
    val name: String,
    val accessLevel: String,
    val linkedDate: String,
    val brandColor: Color
)

@Composable
fun AppAccessItem(
    app: ThirdPartyApp,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(app.brandColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(1),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (app.brandColor == Color.White || app.brandColor == Color(0xFFFFFFFF)) Color.Gray else app.brandColor
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = app.accessLevel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                Text(text = app.linkedDate, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Access", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onRemoveClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove Access", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun AccessManagementSheet(app: ThirdPartyApp, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(app.brandColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(app.name.take(1), fontWeight = FontWeight.Bold, color = app.brandColor)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Manage ${app.name} Access",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider()

        Text("Select what this app can access:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

        AccessToggleItem("Control Lighting", true)
        AccessToggleItem("Manage Climate Settings", true)
        AccessToggleItem("View Security Cameras", app.name != "Amazon Alexa")
        AccessToggleItem("Operate Smart Locks", app.name == "Apple HomeKit" || app.name == "Google Home")
        AccessToggleItem("Access Sensor Data", true)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
fun AccessToggleItem(label: String, initialValue: Boolean) {
    var checked by remember { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { checked = !checked }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = { checked = it })
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdPartyAccessScreenPreview() {
    SmartHomeDesignTheme {
        ThirdPartyAccessScreen(onBackClick = {})
    }
}
