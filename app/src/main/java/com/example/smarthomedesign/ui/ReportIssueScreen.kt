package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Info
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
fun ReportIssueScreen(onBackClick: () -> Unit, onSendReport: () -> Unit) {
    var issueDescription by remember { mutableStateOf("") }
    var includeLogs by remember { mutableStateOf(true) }
    var includeDeviceInfo by remember { mutableStateOf(true) }
    var isSending by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report a Problem") },
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
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.BugReport, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Encountered a bug? Tell us what happened and we'll look into it.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text("What's the issue?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = issueDescription,
                onValueChange = { issueDescription = it },
                placeholder = { Text("Please describe the steps to reproduce the issue...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Diagnostic Data", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Include System Logs", style = MaterialTheme.typography.bodyLarge)
                            Text("Technical details about app performance", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Checkbox(checked = includeLogs, onCheckedChange = { includeLogs = it })
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Include Device Info", style = MaterialTheme.typography.bodyLarge)
                            Text("Model, OS version, and screen size", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Checkbox(checked = includeDeviceInfo, onCheckedChange = { includeDeviceInfo = it })
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "This information helps our developers fix the issue faster.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isSending) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Text("Compiling report and logs...", modifier = Modifier.align(Alignment.CenterHorizontally), style = MaterialTheme.typography.labelMedium)
            }

            Button(
                onClick = { 
                    isSending = true
                    // Simulate sending delay
                    onSendReport() 
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = issueDescription.isNotBlank() && !isSending,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit Bug Report")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportIssueScreenPreview() {
    SmartHomeDesignTheme {
        ReportIssueScreen(onBackClick = {}, onSendReport = {})
    }
}
