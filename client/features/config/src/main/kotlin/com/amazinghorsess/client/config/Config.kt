package com.amazinghorsess.client.config

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amazinghorsess.client.config.model.FileNode
import com.amazinghorsess.client.config.model.FileStatus
import com.amazinghorsess.client.config.model.MemoryUsage
import com.amazinghorsess.client.config.model.ScanResult
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter


@Composable
fun ConfigSection() {
    ConfigLoader()
}
@Composable
private fun ConfigLoader(
    viewModel: ConfigViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val memoryUsage by viewModel.memoryUsage.collectAsState()
    val scanResult by viewModel.scanResult.collectAsState()

    ConfigScaffold(
        modifier = modifier,
        onConnectToServer = { url -> viewModel.connectToServer(url) },
        memoryUsage = memoryUsage,
        onStopScanServer = { viewModel.stopScan() },
        onScanServer = {
            viewModel.startScan(
                packageName = "/sdcard/Documents",
                interval = 6L,
                lastFile = null
            )
        },
        scanResult = scanResult
    )
}

@Composable
private fun ConfigScaffold(
    modifier: Modifier,
    onConnectToServer: (String) -> Unit,
    memoryUsage: MemoryUsage?,
    onStopScanServer: () -> Unit,
    onScanServer: () -> Unit,
    scanResult: ScanResult?
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        ConfigContent(
            memoryUsage = memoryUsage,
            modifier = Modifier.padding(padding),
            onConnectToServer = onConnectToServer,
            onStopScanServer = onStopScanServer,
            onScanServer = onScanServer,
            scanResult = scanResult,
        )
    }
}

@Composable
private fun ConfigContent(
    memoryUsage: MemoryUsage?,
    modifier: Modifier = Modifier,
    onConnectToServer: (String) -> Unit,
    onStopScanServer: () -> Unit,
    onScanServer: () -> Unit,
    scanResult: ScanResult?
) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        MemoryUsageSection(memoryUsage)
        ServerControlSection(
            onConnectToServer = onConnectToServer,
            onStopScanServer = onStopScanServer,
            onScanServer = onScanServer
        )
        ScanResultView(scanResult = scanResult)
    }
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
    onConnectToServer: (String) -> Unit,
    onStopScanServer: () -> Unit,
    onScanServer: () -> Unit,
) {
    var url by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Url") },
            placeholder = {
                Text("0.0.0.0:0000")
            }

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onConnectToServer(url) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Connect Server")
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onScanServer,

                modifier = Modifier.weight(1f)
            ) {
                Text("Start Scan")
            }
            Button(
                onClick = onStopScanServer,
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop Scan")
            }

        }
    }
}
@Composable
fun ScanResultView(scanResult: ScanResult?) {
    LazyColumn {
        item {
            Text("Scan started at: ${scanResult?.scanStartTime?.format()}")
            Text("Scan duration: ${scanResult?.scanDurationMs} ms")
            Text("Total size: ${scanResult?.totalSize?.formatSize()}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("File Tree:")
        }

        if (scanResult != null && scanResult.fileTree != null) {
            item {
                FileTreeItems(scanResult.fileTree)
            }
        } else {
            item {
                Text("No scan results yet")
            }
        }
    }
}

@Composable
fun FileTreeItems(fileNode: FileNode, depth: Int = 0) {
    var expanded by remember { mutableStateOf(depth < 2) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { if (fileNode.isDirectory) expanded = !expanded }
                .padding(start = (depth * 16).dp, top = 4.dp, bottom = 4.dp)
        ) {
            if (fileNode.isDirectory) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
            Text(
                text = if (fileNode.isDirectory) "📁 " else "📄 ",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(fileNode.name, modifier = Modifier.weight(1f))
            Text("(${fileNode.size.formatSize()})")
            Spacer(modifier = Modifier.width(8.dp))
            when (fileNode.status) {
                FileStatus.NEW -> Text("NEW", color = Color.Green)
                FileStatus.MODIFIED -> Text("MODIFIED", color = Color.Cyan)
                FileStatus.UNCHANGED -> Text("UNCHANGED", color = Color.Red)
            }
        }

        if (fileNode.isDirectory && expanded) {
            fileNode.children.forEach { childNode ->
                FileTreeItems(childNode, depth + 1)
            }
        }
    }
}

private fun Long.formatSize(): String {
    val units = listOf("B", "KB", "MB", "GB", "TB")
    var size = this.toDouble()
    var unitIndex = 0
    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }
    return "%.2f %s".format(size, units[unitIndex])
}
private fun Instant.format(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return formatter.format(localDateTime.toJavaLocalDateTime())
}









