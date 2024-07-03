    package com.amazinghorsess.main.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.amazinghorsess.domain.model.FileNode
import com.amazinghorsess.domain.model.FileStatus
import com.amazinghorsess.domain.model.ScanResult
import com.amazinghorsess.main.model.MemoryUsage
import com.amazinghorsess.main.model.ServerStatus
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.time.format.DateTimeFormatter

    @Composable
fun MainScreen(){
MainLoader()
}

@Composable
private fun MainLoader(
    viewModel: MainViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val memoryUsage by viewModel.memoryUsage.collectAsState()

    MainScaffold(
        memoryUsage = memoryUsage,
        onStartServer = { port -> viewModel.startServer(port) },
        onStopServer = { viewModel.stopServer() },
        modifier = modifier
    )

}

@Composable
internal fun MainScaffold(
    memoryUsage: MemoryUsage?,
    onStartServer: (Int) -> Unit,
    onStopServer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .padding(16.dp)
                ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RequestPermissionButton()
            MainContent(
                memoryUsage = memoryUsage,
                onStartServer = onStartServer,
                onStopServer = onStopServer
            )

        }
    }
}

    @Composable
    fun RequestPermissionButton() {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                println("Permission granted")
            } else {
                println("Permission denied")
            }
        }

        Button(onClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        println("Permission already granted")
                    }

                    else -> {
                        launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            } else {
                println("For Android 11 and above, use MANAGE_EXTERNAL_STORAGE")
            }
        }) {
            Text("Request WRITE_EXTERNAL_STORAGE Permission")
        }
    }


@Composable
private fun MainContent(
    memoryUsage: MemoryUsage?,
    onStartServer: (Int) -> Unit,
    onStopServer: () -> Unit,
){
    MemoryUsageSection(memoryUsage)
    ServerControlSection(
        onStartServer = onStartServer,
        onStopServer = onStopServer
    )
}


@Composable
private fun MemoryUsageSection(memoryUsage: MemoryUsage?) {
    Column {
        Text(
            text = "Memory Usage:",
            style = MaterialTheme.typography.titleMedium
        )
        if (memoryUsage != null) {
            Text("Used: ${memoryUsage.used / 1024 / 1024} MB")
            Text("Max: ${memoryUsage.max / 1024 / 1024} MB")
        } else {
            Text("No data available")
        }
    }
}
@Composable
private fun ServerControlSection(
    onStartServer: (Int) -> Unit,
    onStopServer: () -> Unit
) {
    var port by remember { mutableStateOf("8080") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = port,
            onValueChange = { port = it },
            label = { Text("Url") },

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onStartServer(port.toIntOrNull() ?: 8080) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Server")
            }
            Button(
                onClick = onStopServer,
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop Server")
            }
        }
    }
}





