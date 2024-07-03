package com.amazinghorsess.server_app.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.amazinghorsess.designsystem.TzNtiTheme
import com.amazinghorsess.main.presentation.MainScreen

class ServerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            TzNtiTheme {
                MainScreen()
            }

        }
    }

}



