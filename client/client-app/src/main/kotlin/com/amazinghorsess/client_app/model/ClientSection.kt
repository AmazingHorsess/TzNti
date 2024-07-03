package com.amazinghorsess.client_app.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatListNumberedRtl
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class ClientSection(
    val title: String,
    val icon: ImageVector
) {

    Config(
        title = "Config",
        icon = Icons.Outlined.Settings
    ),
    ScanList(
        title = "Scan List",
        icon = Icons.Outlined.FormatListNumberedRtl
    )



}