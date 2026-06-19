package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.model.UserProfile
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricSettingsScreen(
    userProfile: UserProfile,
    onBackClick: () -> Unit,
    onToggleFingerprint: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biometric Login") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Use biometrics for faster and more secure access to your smart home.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Fingerprint Lock", style = MaterialTheme.typography.bodyLarge)
                    Text("Unlock using your fingerprint", style = MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = userProfile.isFingerprintEnabled,
                    onCheckedChange = onToggleFingerprint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BiometricSettingsScreenPreview() {
    SmartHomeDesignTheme {
        BiometricSettingsScreen(
            userProfile = UserProfile(),
            onBackClick = {},
            onToggleFingerprint = {}
        )
    }
}
