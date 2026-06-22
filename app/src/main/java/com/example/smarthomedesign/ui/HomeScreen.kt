package com.example.smarthomedesign.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthomedesign.R
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    onProfileClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Lighting") }
    var showAllCategories by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val categories = listOf(
        CategoryItem("Lighting", Icons.Default.Lightbulb),
        CategoryItem("Climate", Icons.Default.Thermostat),
        CategoryItem("Security", Icons.Default.Security),
        CategoryItem("Music", Icons.Default.MusicNote),
        CategoryItem("Kitchen", Icons.Default.Kitchen),
        CategoryItem("Bedroom", Icons.Default.Bed),
        CategoryItem("Garage", Icons.Default.Garage),
        CategoryItem("Garden", Icons.Default.Grass)
    )

    val devices = remember(selectedCategory) {
        when (selectedCategory) {
            "Lighting" -> listOf(
                DeviceItem("Living Room Light", "On", Icons.Default.Lightbulb, true),
                DeviceItem("Kitchen Light", "Off", Icons.Default.Light, false),
                DeviceItem("Bedroom Lamp", "On", Icons.Default.TipsAndUpdates, true),
                DeviceItem("Dining Light", "Off", Icons.Default.Light, false)
            )
            "Climate" -> listOf(
                DeviceItem("Smart Thermostat", "22°C", Icons.Default.Thermostat, true),
                DeviceItem("Air Purifier", "On", Icons.Default.Air, true),
                DeviceItem("Humidifier", "Off", Icons.Default.WaterDrop, false)
            )
            "Security" -> listOf(
                DeviceItem("Main Door", "Locked", Icons.Default.Lock, true),
                DeviceItem("Kitchen Camera", "Live", Icons.Default.Videocam, true),
                DeviceItem("Living Room Camera", "Inactive", Icons.Default.Videocam, false)
            )
            "Kitchen" -> listOf(
                DeviceItem("Kitchen Camera", "Monitoring", Icons.Default.Videocam, true),
                DeviceItem("Smart Fridge", "Online", Icons.Default.Kitchen, true),
                DeviceItem("Dishwasher", "Finished", Icons.Default.Countertops, false),
                DeviceItem("Oven", "Off", Icons.Default.Microwave, false)
            )
            "Garage" -> listOf(
                DeviceItem("Garage Door", "Closed", Icons.Default.Garage, true),
                DeviceItem("Garage Light", "Off", Icons.Default.Lightbulb, false)
            )
            else -> listOf(
                DeviceItem("Smart Hub", "Online", Icons.Default.Hub, true)
            )
        }
    }

    Scaffold(
        topBar = { HomeTopBar(userName = userName, onProfileClick = onProfileClick) },
        containerColor = Color(0xFF050A19) // Deep dark blue like the image
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SummaryCard()
            }
            item {
                SystemStatusRow()
            }
            item {
                Column {
                    SectionTitle(
                        title = "Favorites",
                        onSeeAllClick = { /* Handle See all Favorites */ }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(end = 16.dp)
                    ) {
                        val favorites = listOf(
                            DeviceItem("Main Door", "Locked", Icons.Default.Lock, true),
                            DeviceItem("Smart Thermostat", "22°C", Icons.Default.Thermostat, true),
                            DeviceItem("Garage Door", "Closed", Icons.Default.Garage, true)
                        )
                        items(favorites) { device ->
                            DeviceCard(
                                device = device,
                                modifier = Modifier.width(160.dp)
                            )
                        }
                    }
                }
            }
            item {
                Column {
                    SectionTitle(
                        title = "Categories",
                        onSeeAllClick = { showAllCategories = true }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CategoriesList(
                        categories = categories.take(4),
                        selectedCategory = selectedCategory,
                        onCategorySelect = { selectedCategory = it }
                    )
                }
            }
            item {
                Column {
                    SectionTitle(
                        title = "Room Design",
                        onSeeAllClick = { /* Handle See all Room Design */ }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    RoomDesignCard()
                }
            }
            item {
                SectionTitle(title = "$selectedCategory Devices")
            }
            
            items(devices.chunked(2)) { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    pair.forEach { device ->
                        DeviceCard(
                            device = device,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        if (showAllCategories) {
            ModalBottomSheet(
                onDismissRequest = { showAllCategories = false },
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "All Categories",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(categories) { category ->
                            CategoryCard(
                                category = category,
                                isSelected = selectedCategory == category.name,
                                onClick = {
                                    selectedCategory = category.name
                                    showAllCategories = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(userName: String, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome Home,",
                style = MaterialTheme.typography.titleSmall,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Surface(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() },
            color = Color(0xFF1E2633),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(28.dp),
                    tint = Color(0xFF80D8FF)
                )
            }
        }
    }
}

@Composable
fun SummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0D1B2A), Color(0xFF1B263B))
                    )
                )
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(240.dp)
                    .offset(x = 40.dp, y = 40.dp),
                tint = Color(0xFF80D8FF).copy(alpha = 0.05f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "My Smart Home",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "24°C",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Cloud, contentDescription = null, tint = Color.White)
                            Text("Cloudy", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingTechIcon(Icons.Default.WaterDrop, "Water")
                    FloatingTechIcon(Icons.Default.Timer, "Timer")
                    FloatingTechIcon(Icons.Default.Lightbulb, "Light")
                    FloatingTechIcon(Icons.Default.Thermostat, "Temp")
                    FloatingTechIcon(Icons.Default.Videocam, "Camera")
                }
            }
            
            Image(
                painter = painterResource(id = R.drawable.ic_smart_home_hero),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(180.dp)
                    .offset(x = 10.dp, y = -20.dp),
                alpha = 0.6f
            )
        }
    }
}

@Composable
fun FloatingTechIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF80D8FF).copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF80D8FF)
            )
        }
    }
}

@Composable
fun SystemStatusRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatusBadge("System", "Online", Icons.Default.CheckCircle, Color(0xFF4CAF50), Modifier.weight(1f))
        StatusBadge("Security", "Armed", Icons.Default.Shield, Color(0xFF80D8FF), Modifier.weight(1f))
        StatusBadge("Storage", "85%", Icons.Default.Dns, Color(0xFFFF9800), Modifier.weight(1f))
    }
}

@Composable
fun StatusBadge(label: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1B263B), // Dark blue status card
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelSmall, color = Color.LightGray)
                Text(value, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, onSeeAllClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        TextButton(onClick = onSeeAllClick) {
            Text(
                text = "See all",
                color = Color(0xFF80D8FF),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<CategoryItem>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                isSelected = selectedCategory == category.name,
                onClick = { onCategorySelect(category.name) }
            )
        }
    }
}

data class CategoryItem(val name: String, val icon: ImageVector)

@Composable
fun CategoryCard(
    category: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF0061A4) else Color(0xFF1B263B),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) Color.White else Color(0xFF80D8FF)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.LightGray
            )
        }
    }
}

@Composable
fun RoomDesignCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2633))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Living Room",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "3 active devices in this area",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0061A4)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text("Manage", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            Icon(
                imageVector = Icons.Default.Chair,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                tint = Color(0xFF80D8FF).copy(alpha = 0.6f)
            )
        }
    }
}

data class DeviceItem(val name: String, val status: String, val icon: ImageVector, val isOn: Boolean)

@Composable
fun DeviceCard(device: DeviceItem, modifier: Modifier = Modifier) {
    var isToggled by remember { mutableStateOf(device.isOn) }
    
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isToggled) Color(0xFF1E3A5F) else Color(0xFF1B263B)
        ),
        modifier = modifier.height(160.dp),
        onClick = { isToggled = !isToggled }
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = device.icon,
                contentDescription = device.name,
                modifier = Modifier.size(36.dp),
                tint = if (isToggled) Color(0xFF80D8FF) else Color.LightGray
            )
            
            Column {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = if (isToggled) "On" else "Off",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isToggled) Color(0xFF80D8FF).copy(alpha = 0.7f) else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SmartHomeDesignTheme {
        HomeScreen(userName = "Vinod Kumar", onProfileClick = {})
    }
}
