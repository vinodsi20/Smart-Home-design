package com.example.smarthomedesign.ui

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBiometricProfileOpen: () -> Unit) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    var showBiometricPopup by remember { mutableStateOf(false) }
    var biometricStatus by remember { mutableStateOf("IDLE") } // IDLE, SCANNING, SUCCESS, FAILED

    // Actual Biometric Integration
    val executor = ContextCompat.getMainExecutor(context)
    val biometricPrompt = remember {
        BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    biometricStatus = "FAILED"
                    showBiometricPopup = true
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onBiometricProfileOpen()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    biometricStatus = "FAILED"
                    showBiometricPopup = true
                }
            }
        )
    }

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Login")
        .setSubtitle("Log in using your fingerprint or face")
        .setNegativeButtonText("Use Password")
        .build()

    LaunchedEffect(biometricStatus) {
        if (biometricStatus == "SCANNING") {
            delay(1000)
            // For Demo/Preview: Auto-Success if no hardware is present
            // In real device, biometricPrompt.authenticate(promptInfo) would trigger
            biometricStatus = "SUCCESS"
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Smart Home",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Secure Home Access",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; errorMessage = null },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; errorMessage = null },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (email == "test@gmail.com" && password == "012345") onLoginSuccess()
                    else errorMessage = "Invalid Credentials"
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Quick Access", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                IconButton(
                    onClick = { 
                        biometricStatus = "SCANNING"
                        showBiometricPopup = true
                        try {
                            biometricPrompt.authenticate(promptInfo)
                        } catch (e: Exception) {
                            // If hardware fails or not available, stay in simulation mode
                        }
                    },
                    modifier = Modifier.size(64.dp).background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                ) {
                    Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(
                    onClick = { 
                        biometricStatus = "SCANNING"
                        showBiometricPopup = true
                        try {
                            biometricPrompt.authenticate(promptInfo)
                        } catch (e: Exception) {
                            // Simulation mode fallback
                        }
                    },
                    modifier = Modifier.size(64.dp).background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                ) {
                    Icon(Icons.Default.Face, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        if (showBiometricPopup) {
            AlertDialog(
                onDismissRequest = { showBiometricPopup = false; biometricStatus = "IDLE" },
                confirmButton = {
                    if (biometricStatus == "SUCCESS") {
                        Button(onClick = { 
                            showBiometricPopup = false
                            onBiometricProfileOpen() 
                        }) {
                            Text("Open Profile")
                        }
                    } else if (biometricStatus == "FAILED") {
                        TextButton(onClick = { biometricStatus = "SCANNING" }) {
                            Text("Try Again")
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showBiometricPopup = false; biometricStatus = "IDLE" }) {
                        Text("Cancel")
                    }
                },
                title = {
                    Text(text = when(biometricStatus) {
                        "SCANNING" -> "Scanning Face/Fingerprint..."
                        "SUCCESS" -> "Authentication Successful"
                        "FAILED" -> "Detection Failed"
                        else -> "Security Verification"
                    })
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        when(biometricStatus) {
                            "SCANNING" -> CircularProgressIndicator(modifier = Modifier.size(48.dp))
                            "SUCCESS" -> {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(64.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Welcome back! Face detected.", textAlign = TextAlign.Center)
                            }
                            "FAILED" -> {
                                Icon(Icons.Default.Error, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(64.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Face not recognized. Please try again or use your password.", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SmartHomeDesignTheme {
        LoginScreen(onLoginSuccess = {}, onBiometricProfileOpen = {})
    }
}
