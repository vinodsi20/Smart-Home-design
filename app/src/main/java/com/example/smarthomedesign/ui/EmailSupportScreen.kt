package com.example.smarthomedesign.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
fun EmailSupportScreen(
    userEmail: String = "user@example.com",
    onBackClick: () -> Unit,
    onSendClick: () -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Technical Issue") }
    var recipientEmail by remember { mutableStateOf("support@smarthomedesign.com") }
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedRecipient by remember { mutableStateOf(false) }
    
    var showAttachmentSource by remember { mutableStateOf(false) }
    
    val attachments = remember { mutableStateListOf<String>() }
    val sheetState = rememberModalBottomSheetState()

    // Real system launcher for Album/Gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            attachments.add("Album_Image_${attachments.size + 1}.jpg")
            showAttachmentSource = false
        }
    }

    // Real system launcher for Camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            attachments.add("Camera_Photo_${attachments.size + 1}.jpg")
            showAttachmentSource = false
        }
    }

    val categories = listOf("Technical Issue", "Account Problem", "Device Pairing", "Feedback", "Other")
    val recipients = listOf(
        "Technical Support" to "support@smarthomedesign.com",
        "Sales & Billing" to "billing@smarthomedesign.com",
        "Partnerships" to "partners@smarthomedesign.com",
        "Security Team" to "security@smarthomedesign.com"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Email Support") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAttachmentSource = true }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach File")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Compose Support Email",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // From Field
            OutlinedTextField(
                value = userEmail,
                onValueChange = {},
                readOnly = true,
                label = { Text("From") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                )
            )

            // Recipient Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedRecipient,
                onExpandedChange = { expandedRecipient = !expandedRecipient }
            ) {
                OutlinedTextField(
                    value = recipientEmail,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("To") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRecipient) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
                )
                ExposedDropdownMenu(
                    expanded = expandedRecipient,
                    onDismissRequest = { expandedRecipient = false }
                ) {
                    recipients.forEach { (name, email) ->
                        DropdownMenuItem(
                            text = { 
                                Column {
                                    Text(name, fontWeight = FontWeight.Bold)
                                    Text(email, style = MaterialTheme.typography.labelSmall)
                                }
                            },
                            onClick = {
                                recipientEmail = email
                                expandedRecipient = false
                            }
                        )
                    }
                }
            }

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = !expandedCategory }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expandedCategory = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                placeholder = { Text("Briefly describe the issue") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                placeholder = { Text("Describe your problem in detail...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // Attachments List
            if (attachments.isNotEmpty()) {
                Text(
                    text = "Attachments (${attachments.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    attachments.forEachIndexed { index, fileName ->
                        InputChip(
                            selected = true,
                            onClick = { attachments.removeAt(index) },
                            label = { Text(fileName) },
                            leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(18.dp)) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSendClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = subject.isNotBlank() && message.isNotBlank()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Send Email")
            }
        }

        // Attachment Source Selector
        if (showAttachmentSource) {
            ModalBottomSheet(
                onDismissRequest = { showAttachmentSource = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Attach from",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AttachmentSourceItem(
                            title = "Camera",
                            icon = Icons.Default.PhotoCamera,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = { cameraLauncher.launch(null) }
                        )
                        AttachmentSourceItem(
                            title = "Album",
                            icon = Icons.Default.Collections,
                            color = Color(0xFFFF9800),
                            onClick = { galleryLauncher.launch("image/*") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AttachmentSourceItem(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    tint = color
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmailSupportScreenPreview() {
    SmartHomeDesignTheme {
        EmailSupportScreen(onBackClick = {}, onSendClick = {})
    }
}
