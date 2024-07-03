package com.amazinghorsess.client_app.presentation.home

import com.amazinghorsess.client_app.model.ClientSection

internal data class ClientActions(
    val onConfigClick: () -> Unit = {},
    val onScanListClick: () -> Unit = {},
    val setCurrentSection: (ClientSection) -> Unit = {},
)