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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        topBar = { HomeTopBar(userName = userName, onProfileClick = onProfileClick) }
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
                Column {
                    SectionTitle(title = "Favorites")
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
                    SectionTitle(title = "Room Design")
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
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome Home,",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_smart_home_hero),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(140.dp)
                    .offset(x = 20.dp, y = 10.dp),
                alpha = 0.4f,
                contentScale = ContentScale.Fit
            )
            
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "24°C",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Indoor Temperature",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Cloudy",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Humidity: 45%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
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
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onSeeAllClick) {
            Text(text = "See all")
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
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                modifier = Modifier.size(20.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
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
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Living Room",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "3 active devices in this area",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Manage")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.ic_living_room),
                contentDescription = "Living Room Layout",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

data class DeviceItem(val name: String, val status: String, val icon: ImageVector, val isOn: Boolean)

@Composable
fun DeviceCard(device: DeviceItem, modifier: Modifier = Modifier) {
    var isToggled by remember { mutableStateOf(device.isOn) }
    
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isToggled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier,
        onClick = { isToggled = !isToggled }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = device.icon,
                contentDescription = device.name,
                modifier = Modifier.size(32.dp),
                tint = if (isToggled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = device.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isToggled) "On" else "Off",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
