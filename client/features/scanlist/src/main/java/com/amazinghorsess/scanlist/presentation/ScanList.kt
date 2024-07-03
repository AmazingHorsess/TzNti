package com.amazinghorsess.scanlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amazinghorsess.scanlist.model.ScanResult
import org.koin.androidx.compose.koinViewModel
@Composable
fun ScanListSection(){
    ScanListLoader()
}

@Composable
private fun ScanListLoader(
    viewModel: ScanListViewModel = koinViewModel(),
){
    val scan = viewModel.scanHistory.collectAsState()
    ScanListScaffold(
        onItemClick = {  },
        scan = scan.value
    )

}

@Composable
private fun ScanListScaffold(
    onItemClick: (Int) -> Unit,
    scan: List<ScanResult>,

    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        ScanListContent(
            modifier = Modifier.padding(padding),
            onItemClick = onItemClick,
            scan = scan
        )
    }

}

@Composable
private fun ScanListContent(
    scan: List<ScanResult>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier



){
    LazyColumn {
        items(scan){ scan ->
            ScanHistoryItem(scan = scan, onItemClick)

        }

    }

}
@Composable
private fun ScanHistoryItem(
    scan: ScanResult,
    onRestoreClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Scan ID: ${scan.id}")
            Text(text = "Start Time: ${scan.scanStartTime}")
            Text(text = "Total Size: ${scan.totalSize} bytes")
            Text(text = "Duration: ${scan.scanDurationMs} ms")
        }
        Row(horizontalArrangement = Arrangement.End) {
            Button(onClick = {onRestoreClick}) {
                Text(text = "Restore scan")

            }

        }

    }
}