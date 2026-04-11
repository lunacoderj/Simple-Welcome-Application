package com.mad.app.ui.recyclerview

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.app.data.model.AndroidVersion
import com.mad.app.data.model.AndroidVersionRepository
import com.mad.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclerViewScreen(onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var isGridMode by remember { mutableStateOf(false) }
    var versions by remember { mutableStateOf(AndroidVersionRepository.getVersions()) }
    var deletedItem by remember { mutableStateOf<AndroidVersion?>(null) }
    var showUndo by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val filteredVersions = versions.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.codename.contains(searchQuery, ignoreCase = true) ||
                it.apiLevel.toString().contains(searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Android Versions", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                // Toggle grid/list
                IconButton(onClick = {
                    isGridMode = !isGridMode
                }) {
                    AnimatedContent(
                        targetState = isGridMode,
                        transitionSpec = {
                            scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut()
                        },
                        label = "layoutToggle"
                    ) { grid ->
                        Icon(
                            if (grid) Icons.Filled.ViewList else Icons.Filled.GridView,
                            contentDescription = "Toggle layout"
                        )
                    }
                }
            }
        )

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search versions...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        // Pull to refresh simulation
        if (isRefreshing) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColor
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (filteredVersions.isEmpty()) {
                // Empty state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🔍", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No results found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Try a different search term",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                AnimatedContent(
                    targetState = isGridMode,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },
                    label = "layoutSwitch"
                ) { grid ->
                    if (grid) {
                        // Grid mode
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(
                                filteredVersions,
                                key = { _, item -> item.id }
                            ) { index, version ->
                                VersionGridItem(
                                    version = version,
                                    index = index,
                                    onDelete = {
                                        deletedItem = version
                                        versions = versions.filter { it.id != version.id }
                                        showUndo = true
                                        scope.launch {
                                            delay(3000)
                                            showUndo = false
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        // List mode
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 8.dp,
                                bottom = 80.dp
                            )
                        ) {
                            itemsIndexed(
                                filteredVersions,
                                key = { _, item -> item.id }
                            ) { index, version ->
                                VersionListItem(
                                    version = version,
                                    index = index,
                                    onDelete = {
                                        deletedItem = version
                                        versions = versions.filter { it.id != version.id }
                                        showUndo = true
                                        scope.launch {
                                            delay(3000)
                                            showUndo = false
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // FAB for refresh
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        isRefreshing = true
                        delay(1500)
                        versions = AndroidVersionRepository.getVersions()
                        isRefreshing = false
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = PrimaryColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
            }

            // Undo Snackbar
            AnimatedVisibility(
                visible = showUndo,
                enter = slideInVertically(
                    spring(dampingRatio = 0.6f),
                    initialOffsetY = { it }
                ) + fadeIn(),
                exit = slideOutVertically(
                    tween(200),
                    targetOffsetY = { it }
                ) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF323232)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Item deleted",
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(
                            onClick = {
                                deletedItem?.let { item ->
                                    versions = (versions + item).sortedBy { it.id }
                                }
                                showUndo = false
                            }
                        ) {
                            Text("UNDO", color = SecondaryColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VersionListItem(
    version: AndroidVersion,
    index: Int,
    onDelete: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }

    LaunchedEffect(version.id) {
        delay(index * 50L)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(300)) + slideInVertically(
            tween(300),
            initialOffsetY = { it / 4 }
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .offset(x = offsetX.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (kotlin.math.abs(offsetX) > 100) {
                                onDelete()
                            }
                            offsetX = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX += dragAmount * 0.5f
                        }
                    )
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emoji icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(PrimaryColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(version.emoji, fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        version.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${version.codename} • ${version.releaseDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PrimaryColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        "API ${version.apiLevel}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                }
            }
        }
    }
}

@Composable
fun VersionGridItem(
    version: AndroidVersion,
    index: Int,
    onDelete: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(version.id) {
        delay(index * 60L)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(300)) + scaleIn(tween(300), initialScale = 0.8f)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier.clickable { }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(version.emoji, fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    version.codename,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "API ${version.apiLevel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PrimaryColor
                )
                Text(
                    version.releaseDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
