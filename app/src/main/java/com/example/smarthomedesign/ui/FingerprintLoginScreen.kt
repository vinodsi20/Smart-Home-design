package com.example.smarthomedesign.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smarthomedesign.R
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme
import kotlinx.coroutines.delay

@Composable
fun FingerprintLoginScreen(
    onSuccess: () -> Unit,
    onUsePassword: () -> Unit
) {
    var isScanning by remember { mutableStateOf(false) }
    var authState by remember { mutableStateOf("IDLE") } // IDLE, SCANNING, SUCCESS, ERROR

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(isScanning) {
        if (isScanning) {
            authState = "SCANNING"
            delay(2000)
            authState = "SUCCESS"
            delay(800)
            onSuccess()
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
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_smart_home_hero),
                    contentDescription = null,
                    modifier = Modifier.size(90.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Fingerprint Login",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Text(
                text = "Please place your finger on the sensor to continue",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp)
            ) {
                // Pulse effect when idle
                if (authState == "IDLE") {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(pulseScale)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = CircleShape
                            )
                    )
                }

                if (authState == "SCANNING") {
                    CircularProgressIndicator(
                        modifier = Modifier.size(140.dp),
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Surface(
                    onClick = { if (authState == "IDLE") isScanning = true },
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape),
                    color = when (authState) {
                        "SUCCESS" -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                        "SCANNING" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = CircleShape
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = "Fingerprint Sensor",
                            modifier = Modifier.size(64.dp),
                            tint = when (authState) {
                                "SUCCESS" -> Color(0xFF4CAF50)
                                "SCANNING" -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = when(authState) {
                    "SCANNING" -> "Verifying identity..."
                    "SUCCESS" -> "Access Granted"
                    else -> "Touch to Scan"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = when(authState) {
                    "SUCCESS" -> Color(0xFF4CAF50)
                    "SCANNING" -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(modifier = Modifier.height(100.dp))

            Button(
                onClick = onUsePassword,
                colors = ButtonDefaults.textButtonColors(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "USE PASSWORD",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FingerprintLoginScreenPreview() {
    SmartHomeDesignTheme {
        FingerprintLoginScreen(onSuccess = {}, onUsePassword = {})
    }
}
