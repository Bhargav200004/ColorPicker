package com.example.colorpickerproject.ui.colorScreen

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.colorpickerproject.utils.toColorLong


@Composable
fun ColorScreen(modifier: Modifier = Modifier) {

    val viewModel: ColorScreenViewModel = hiltViewModel()

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val task by viewModel.tasks.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            ColorScreenTopAppBar(
                syncNumber = uiState.syncNumber,
                isSyncing = uiState.isSyncing,
                onSyncClick = { viewModel.onEvent(ColorScreenEvent.OnClickSyncButton) }
            )
        }
    ) { paddingValues ->


        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier

            ) {
                items(task) {
                    ColorCard(
                        color = it.color,
                        date = it.dateTime,
                        backgroungColor = it.color.toColorLong()
                    )
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.BottomEnd),

                onClick = { viewModel.onEvent(ColorScreenEvent.OnClickAddNewColor) }
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Color",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null
                    )

                }
            }
        }
    }
}


@Composable
private fun ColorCard(
    color: String,
    date: String,
    backgroungColor: Long
) {
    OutlinedCard(
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(backgroungColor)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = color,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White.copy(alpha = 0.8f),
                    ),

                    )
                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 2.dp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }

            Text(
                text = "CREATED AT\n$date",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White.copy(alpha = 0.8f),
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreenTopAppBar(
    syncNumber: String,
    isSyncing: Boolean,
    onSyncClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .padding(top = 18.dp),
        title = {
            Text("Color App")
        },
        actions = {
            IconButton(
                modifier = Modifier.width(70.dp),
                onClick = { onSyncClick() }
            ) {
                SyncingIcon(syncNumber = syncNumber, isSyncing = isSyncing)
            }


        },
        windowInsets = WindowInsets.statusBars.only(WindowInsetsSides.Horizontal)
    )
}


@Composable
fun SyncingIcon(syncNumber: String, isSyncing: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotationAngle by infiniteTransition.animateFloat(
        label = "",
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(syncNumber)

        Icon(
            imageVector = Icons.Outlined.Sync,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer(
                    rotationZ = if (isSyncing) rotationAngle else 0f
                )
        )
    }
}


