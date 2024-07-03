package com.amazinghorsess.client_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.amazinghorsess.client.config.ConfigSection
import com.amazinghorsess.client_app.navigation.NavGraph
import com.amazinghorsess.designsystem.TzNtiTheme

class ClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TzNtiTheme {
              NavGraph()
            }
        }
    }
}




