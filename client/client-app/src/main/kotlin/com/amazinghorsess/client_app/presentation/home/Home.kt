package com.amazinghorsess.client_app.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.amazinghorsess.client.config.ConfigSection
import com.amazinghorsess.client_app.model.ClientSection
import com.amazinghorsess.scanlist.presentation.ScanListSection


@Composable
fun Home(

    onScanListClick: () -> Unit,


) {
    val (currentSection,setCurrentSection) = rememberSaveable {
        mutableStateOf(ClientSection.Config)
    }
    val navItems = ClientSection.entries.toList()

    val actions = ClientActions(

        onScanListClick = onScanListClick,
        setCurrentSection = setCurrentSection
    )
    Crossfade(currentSection) { homeSection ->
        ClientHomeScaffold(clientSection = homeSection, navItems = navItems , actions = actions)
    }
    onScanListClick()

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClientTopAppBar(
    currentSection: ClientSection,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                text = currentSection.title,
                color = MaterialTheme.colorScheme.tertiary,
            )
        },
    )
}
@Composable
private fun ClientBottomBar(
    currentSection: ClientSection,
    onSectionSelect: (ClientSection) -> Unit,
    items: List<ClientSection>,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEach { section ->
            val selected = section == currentSection
            val title = section.title
            NavigationBarItem(
                selected = selected,
                onClick = { onSectionSelect(section) }, // Передаем текущую секцию
                icon = {
                    Icon(
                        imageVector = section.icon,
                        contentDescription = title
                    )
                },
                label = { Text(text = title) }
            )
        }
    }
}
@Composable
private fun ClientHomeScaffold(
    clientSection: ClientSection,
    navItems: List<ClientSection>,
    actions: ClientActions,
){
    Scaffold (
        topBar = {
            ClientTopAppBar(currentSection = clientSection)
        },
        contentWindowInsets = WindowInsets(0,0,0,0),
        content = {paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    ),

                ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ClientContent(
                        clientSection = clientSection,
                        modifier = Modifier,
                        actions = actions
                    )
                }
            }

        },
        bottomBar = {
            ClientBottomBar(
                currentSection = clientSection,
                onSectionSelect = actions.setCurrentSection,
                items = navItems,
                modifier = Modifier.safeDrawingPadding(),
            )
        }
    )

}


@Composable
private fun ClientContent(
    clientSection: ClientSection,
    actions: ClientActions,
    modifier: Modifier = Modifier
){
    when(clientSection){

        ClientSection.Config ->
            ConfigSection()


        ClientSection.ScanList ->
            ScanListSection()

    }



}
