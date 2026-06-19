package com.example.smarthomedesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthomedesign.ui.*
import com.example.smarthomedesign.ui.theme.SmartHomeDesignTheme
import com.example.smarthomedesign.viewmodel.UserViewModel

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartHomeDesignTheme {
                val userViewModel: UserViewModel = viewModel()
                val userProfile by userViewModel.userProfile.collectAsState()
                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        onLoginSuccess = { currentScreen = "home" },
                        onBiometricProfileOpen = { currentScreen = "profile" }
                    )
                    "home" -> HomeScreen(
                        userName = userProfile.name,
                        onProfileClick = { currentScreen = "profile" }
                    )
                    "profile" -> ProfileScreen(
                        userName = userProfile.name,
                        userEmail = userProfile.email,
                        onBackClick = { currentScreen = "home" },
                        onPersonalInfoClick = { currentScreen = "personal_info" },
                        onHomeManagementClick = { currentScreen = "home_management" },
                        onDevicesAutomationClick = { currentScreen = "devices_automation" },
                        onNotificationsClick = { currentScreen = "notifications" },
                        onSecurityPrivacyClick = { currentScreen = "security_privacy" },
                        onHelpSupportClick = { currentScreen = "help_support" }
                    )
                    "personal_info" -> PersonalInformationScreen(
                        userProfile = userProfile,
                        onBackClick = { currentScreen = "profile" },
                        onSave = { name, email, phone, address ->
                            userViewModel.updateProfile(name, email, phone, address)
                            currentScreen = "profile"
                        }
                    )
                    "home_management" -> HomeManagementScreen(
                        onBackClick = { currentScreen = "profile" },
                        onWifiSetupClick = { currentScreen = "wifi_setup" }
                    )
                    "wifi_setup" -> WifiSetupScreen(onBackClick = { currentScreen = "home_management" })
                    "devices_automation" -> DevicesAutomationScreen(onBackClick = { currentScreen = "profile" })
                    "notifications" -> NotificationsScreen(onBackClick = { currentScreen = "profile" })
                    "security_privacy" -> SecurityPrivacyScreen(
                        onBackClick = { currentScreen = "profile" },
                        onChangePasswordClick = { currentScreen = "change_password" }
                    )
                    "change_password" -> ChangePasswordScreen(
                        onBackClick = { currentScreen = "security_privacy" },
                        onPasswordChanged = { currentScreen = "security_privacy" }
                    )
                    "help_support" -> HelpSupportScreen(onBackClick = { currentScreen = "profile" })
                }
            }
        }
    }
}
