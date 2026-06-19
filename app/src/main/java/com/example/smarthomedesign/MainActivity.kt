package com.example.smarthomedesign

import android.content.Intent
import android.net.Uri
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

                fun openDialer(phoneNumber: String) {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    startActivity(intent)
                }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        userProfile = userProfile,
                        onLoginSuccess = { currentScreen = "home" },
                        onBiometricProfileOpen = { currentScreen = "home" }
                    )
                    "fingerprint_login" -> FingerprintLoginScreen(
                        onSuccess = { currentScreen = "home" },
                        onUsePassword = { currentScreen = "login" }
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
                        onChangePasswordClick = { currentScreen = "change_password" },
                        onBiometricClick = { currentScreen = "biometric_settings" },
                        onTwoFactorClick = { currentScreen = "two_factor_auth" },
                        onLoginHistoryClick = { currentScreen = "login_history" },
                        onActivityLogClick = { currentScreen = "activity_log" },
                        onThirdPartyAccessClick = { currentScreen = "third_party_access" },
                        onDataUsageClick = { currentScreen = "data_usage" },
                        onPrivacyPolicyClick = { currentScreen = "privacy_policy" }
                    )
                    "activity_log" -> ActivityLogScreen(onBackClick = { currentScreen = "security_privacy" })
                    "third_party_access" -> ThirdPartyAccessScreen(onBackClick = { currentScreen = "security_privacy" })
                    "data_usage" -> DataUsageScreen(onBackClick = { currentScreen = "security_privacy" })
                    "privacy_policy" -> PrivacyPolicyScreen(onBackClick = { currentScreen = "security_privacy" })
                    "login_history" -> LoginHistoryScreen(onBackClick = { currentScreen = "security_privacy" })
                    "two_factor_auth" -> TwoFactorAuthScreen(
                        userProfile = userProfile,
                        onBackClick = { currentScreen = "security_privacy" },
                        onToggleTwoFactor = { userViewModel.toggleTwoFactor(it) }
                    )
                    "biometric_settings" -> BiometricSettingsScreen(
                        userProfile = userProfile,
                        onBackClick = { currentScreen = "security_privacy" },
                        onToggleFingerprint = { userViewModel.toggleFingerprint(it) }
                    )
                    "change_password" -> ChangePasswordScreen(
                        onBackClick = { currentScreen = "security_privacy" },
                        onPasswordChanged = { currentScreen = "security_privacy" }
                    )
                    "help_support" -> HelpSupportScreen(
                        onBackClick = { currentScreen = "profile" },
                        onChatClick = { currentScreen = "support_chat" },
                        onCallClick = { openDialer("7875311455") },
                        onEmailClick = { currentScreen = "support_email" },
                        onReportIssueClick = { currentScreen = "report_issue" }
                    )
                    "support_chat" -> SupportChatScreen(onBackClick = { currentScreen = "help_support" })
                    "support_call" -> CallSupportScreen(onEndCall = { currentScreen = "help_support" })
                    "support_email" -> EmailSupportScreen(
                        userEmail = userProfile.email,
                        onBackClick = { currentScreen = "help_support" },
                        onSendClick = { currentScreen = "help_support" }
                    )
                    "report_issue" -> ReportIssueScreen(
                        onBackClick = { currentScreen = "help_support" },
                        onSendReport = { currentScreen = "help_support" }
                    )
                }
            }
        }
    }
}
