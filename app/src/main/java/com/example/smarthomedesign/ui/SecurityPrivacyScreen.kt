package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityPrivacyScreen(
    onBackClick: () -> Unit,
    onChangePasswordClick: () -> Unit = {},
    onBiometricClick: () -> Unit = {},
    onTwoFactorClick: () -> Unit = {},
    onLoginHistoryClick: () -> Unit = {},
    onActivityLogClick: () -> Unit = {},
    onThirdPartyAccessClick: () -> Unit = {},
    onDataUsageClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security & Privacy") },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Account Security",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            val securityOptions = listOf(
                SecurityOption("Change Password", "Update your account password", Icons.Default.Lock, onChangePasswordClick),
                SecurityOption("Two-Factor Authentication", "Add an extra layer of security", Icons.Default.VerifiedUser, onTwoFactorClick),
                SecurityOption("Biometric Login", "Fingerprint and Face ID settings", Icons.Default.Fingerprint, onBiometricClick),
                SecurityOption("Login History", "Check active sessions and devices", Icons.Default.History, onLoginHistoryClick)
            )
            
            items(securityOptions) { option ->
                SecurityRow(option)
            }
            
            item {
                Text(
                    text = "Data & Privacy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            val privacyOptions = listOf(
                SecurityOption("Activity Log", "Manage your device activity history", Icons.Default.ViewList, onActivityLogClick),
                SecurityOption("Third-party Access", "Apps and services with home access", Icons.Default.Apps, onThirdPartyAccessClick),
                SecurityOption("Data Usage", "How we use your smart home data", Icons.Default.Storage, onDataUsageClick),
                SecurityOption("Privacy Policy", "Read our full privacy terms", Icons.Default.Description, onPrivacyPolicyClick)
            )
            
            items(privacyOptions) { option ->
                SecurityRow(option)
            }
        }
    }
}

data class SecurityOption(val title: String, val subtitle: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun SecurityRow(option: SecurityOption) {
    Surface(
        onClick = option.onClick,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = option.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = option.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecurityPrivacyScreenPreview() {
    SmartHomeDesignTheme {
        SecurityPrivacyScreen(
            onBackClick = {},
            onChangePasswordClick = {},
            onBiometricClick = {},
            onTwoFactorClick = {},
            onLoginHistoryClick = {},
            onActivityLogClick = {},
            onThirdPartyAccessClick = {},
            onDataUsageClick = {},
            onPrivacyPolicyClick = {}
        )
    }
}
