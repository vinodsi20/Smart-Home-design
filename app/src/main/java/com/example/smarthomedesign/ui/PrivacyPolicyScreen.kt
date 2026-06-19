package com.example.smarthomedesign.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Smart Home Design Privacy Policy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Last Updated: October 2023",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            PolicySection(
                title = "1. Information We Collect",
                content = "We collect information you provide directly to us, such as when you create an account, set up your smart devices, or contact us for support. This may include your name, email address, phone number, and physical address."
            )

            PolicySection(
                title = "2. How We Use Your Information",
                content = "We use the information we collect to provide, maintain, and improve our services, including to operate your smart home devices, provide customer support, and personalize your experience."
            )

            PolicySection(
                title = "3. Data Security",
                content = "We take reasonable measures to help protect information about you from loss, theft, misuse and unauthorized access, disclosure, alteration and destruction. All communication between the app and our servers is encrypted."
            )

            PolicySection(
                title = "4. Your Choices",
                content = "You may update your account information at any time by logging into your account settings. You can also control the collection of diagnostic data through the 'Data Usage' settings in the app."
            )

            PolicySection(
                title = "5. Third-Party Services",
                content = "Our services may contain links to other websites or services. We are not responsible for the privacy practices of other websites and services and recommend that you review their privacy policies."
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrivacyPolicyScreenPreview() {
    SmartHomeDesignTheme {
        PrivacyPolicyScreen(onBackClick = {})
    }
}
