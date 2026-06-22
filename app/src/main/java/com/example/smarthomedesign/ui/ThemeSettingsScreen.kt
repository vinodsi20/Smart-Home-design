package com.example.smarthomedesign.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(
    currentDarkMode: Boolean?,
    onBackClick: () -> Unit,
    onThemeChange: (Boolean?) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Display & Theme") },
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
                text = "Choose how Smart Home looks to you.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Column(Modifier.selectableGroup()) {
                ThemeOptionRow(
                    icon = Icons.Default.SettingsSuggest,
                    title = "System Default",
                    selected = currentDarkMode == null,
                    onClick = { onThemeChange(null) }
                )
                ThemeOptionRow(
                    icon = Icons.Default.LightMode,
                    title = "Light Mode",
                    selected = currentDarkMode == false,
                    onClick = { onThemeChange(false) }
                )
                ThemeOptionRow(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    selected = currentDarkMode == true,
                    onClick = { onThemeChange(true) }
                )
            }
        }
    }
}

@Composable
fun ThemeOptionRow(
    icon: ImageVector,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selected,
            onClick = null // null because the row handles the click
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSettingsScreenPreview() {
    SmartHomeDesignTheme {
        ThemeSettingsScreen(
            currentDarkMode = null,
            onBackClick = {},
            onThemeChange = {}
        )
    }
}
