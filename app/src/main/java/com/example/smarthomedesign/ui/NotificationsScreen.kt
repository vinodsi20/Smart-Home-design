package com.example.smarthomedesign.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
fun NotificationsScreen(onBackClick: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Inbox", "Preferences")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (selectedTab == 0) {
                        IconButton(onClick = { /* Clear all */ }) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear all")
                        }
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
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> NotificationInbox()
                1 -> NotificationPreferences()
            }
        }
    }
}

@Composable
fun NotificationInbox() {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Security", "Automation", "Energy")

    val allNotifications = listOf(
        NotificationItem("Security Alert", "Motion detected in Living Room at 10:45 PM", "10 min ago", Icons.Default.Security, MaterialTheme.colorScheme.error, "Security"),
        NotificationItem("Automation Success", "Routine 'Good Night' started successfully", "1 hr ago", Icons.Default.AutoMode, MaterialTheme.colorScheme.primary, "Automation"),
        NotificationItem("Energy Report", "Your energy usage was 15% lower this week!", "3 hrs ago", Icons.Default.BarChart, Color(0xFF4CAF50), "Energy"),
        NotificationItem("Device Offline", "Front Door Camera is currently offline", "5 hrs ago", Icons.Default.VideocamOff, MaterialTheme.colorScheme.error, "Security"),
        NotificationItem("Welcome Home", "Living Room AC turned on automatically", "6 hrs ago", Icons.Default.AcUnit, MaterialTheme.colorScheme.primary, "Automation")
    )

    val filteredNotifications = if (selectedFilter == "All") {
        allNotifications
    } else {
        allNotifications.filter { it.category == selectedFilter }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredNotifications) { item ->
                NotificationRow(item)
            }
        }
    }
}

@Composable
fun NotificationPreferences() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Delivery Channels",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        PreferenceToggle("Push Notifications", "Receive alerts on your device", true)
        PreferenceToggle("Email Notifications", "Weekly reports and security summaries", false)
        PreferenceToggle("SMS Alerts", "Critical security alerts via text", true)
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        
        Text(
            text = "Notification Types",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        PreferenceToggle("Security Alerts", "Break-in alerts, motion detection", true)
        PreferenceToggle("System Updates", "New features and app improvements", true)
        PreferenceToggle("Device Status", "Low battery or offline warnings", true)
        PreferenceToggle("Automation Logs", "Daily automation routine results", false)
    }
}

@Composable
fun PreferenceToggle(title: String, subtitle: String, initialState: Boolean) {
    var checked by remember { mutableStateOf(initialState) }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }
    }
}

data class NotificationItem(
    val title: String, 
    val message: String, 
    val time: String, 
    val icon: ImageVector, 
    val iconColor: Color,
    val category: String = "All"
)

@Composable
fun NotificationRow(item: NotificationItem) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(item.iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = item.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(text = item.message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    SmartHomeDesignTheme {
        NotificationsScreen(onBackClick = {})
    }
}
