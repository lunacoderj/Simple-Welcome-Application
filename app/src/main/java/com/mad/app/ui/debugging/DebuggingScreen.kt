package com.mad.app.ui.debugging

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.app.ui.theme.*
import kotlinx.coroutines.delay
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

data class LogEntry(
    val level: String,
    val message: String,
    val timestamp: String
)

data class CrashReport(
    val exceptionType: String,
    val message: String,
    val stackTrace: String,
    val timestamp: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebuggingScreen(onBack: () -> Unit) {
    var crashReport by remember { mutableStateOf<CrashReport?>(null) }
    var isFixed by remember { mutableStateOf(false) }
    var showCrashCard by remember { mutableStateOf(false) }
    val logs = remember { mutableStateListOf<LogEntry>() }
    val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    fun addLog(level: String, msg: String) {
        logs.add(0, LogEntry(level, msg, dateFormat.format(Date())))
        when (level) {
            "DEBUG" -> Timber.d(msg)
            "WARN" -> Timber.w(msg)
            "ERROR" -> Timber.e(msg)
        }
    }

    fun triggerCrash() {
        addLog("DEBUG", "Attempting risky operation...")
        addLog("WARN", "Operating on potentially null data")

        try {
            val emptyList: List<String> = emptyList()
            // This will throw IndexOutOfBoundsException
            val item = emptyList[0]
            addLog("DEBUG", "Got item: $item")
        } catch (e: Exception) {
            addLog("ERROR", "Exception caught: ${e::class.simpleName}")

            crashReport = CrashReport(
                exceptionType = e::class.simpleName ?: "Unknown",
                message = e.message ?: "No message",
                stackTrace = e.stackTraceToString().take(1500),
                timestamp = dateFormat.format(Date())
            )
            showCrashCard = true
        }
    }

    fun fixAndRetry() {
        addLog("DEBUG", "Applying fix: initializing list with default data")
        addLog("DEBUG", "Creating safe list access with .getOrNull()")

        val safeList = listOf("Fixed Item 1", "Fixed Item 2", "Fixed Item 3")
        val item = safeList.getOrNull(0) ?: "Default"

        addLog("DEBUG", "Successfully retrieved: $item")
        addLog("DEBUG", "Operation completed without errors ✓")

        isFixed = true
        showCrashCard = false
        crashReport = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Debugging", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Trigger button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        isFixed = false
                        triggerCrash()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorColor
                    )
                ) {
                    Icon(
                        Icons.Filled.BugReport,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Trigger Crash", fontSize = 13.sp)
                }

                AnimatedVisibility(
                    visible = showCrashCard,
                    enter = fadeIn() + expandHorizontally()
                ) {
                    Button(
                        onClick = { fixAndRetry() },
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Icon(
                            Icons.Filled.Build,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Fix & Retry", fontSize = 13.sp)
                    }
                }
            }

            // Success message
            AnimatedVisibility(
                visible = isFixed,
                enter = fadeIn() + expandVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Bug fixed successfully! Operation completed.",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Crash Report Card (styled like Firebase Crashlytics)
            AnimatedVisibility(
                visible = showCrashCard && crashReport != null,
                enter = fadeIn(tween(300)) + expandVertically(tween(300))
            ) {
                crashReport?.let { report ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF3F0)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Header
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Error,
                                    contentDescription = null,
                                    tint = ErrorColor,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Crash Report",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = ErrorColor
                                    )
                                    Text(
                                        report.timestamp,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Exception type
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ErrorColor.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = report.exceptionType,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = ErrorColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                "Message: ${report.message}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Stack trace
                            Text(
                                "Stack Trace",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF1E1E1E)
                            ) {
                                Text(
                                    text = report.stackTrace,
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState()),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    color = Color(0xFFE0E0E0),
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Log panel
            if (logs.isNotEmpty()) {
                Text(
                    "📋 Log Output",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E1E)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        logs.take(20).forEach { log ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Level badge
                                val (badgeColor, badgeBg) = when (log.level) {
                                    "ERROR" -> Pair(Color.White, ErrorColor)
                                    "WARN" -> Pair(Color.Black, Color(0xFFFFCA28))
                                    else -> Pair(Color.White, Color(0xFF42A5F5))
                                }
                                Surface(
                                    shape = RoundedCornerShape(3.dp),
                                    color = badgeBg
                                ) {
                                    Text(
                                        text = log.level.take(1),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = badgeColor,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${log.timestamp} ",
                                    fontSize = 10.sp,
                                    color = Color(0xFF808080),
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = log.message,
                                    fontSize = 11.sp,
                                    color = when (log.level) {
                                        "ERROR" -> Color(0xFFEF5350)
                                        "WARN" -> Color(0xFFFFCA28)
                                        else -> Color(0xFF69F0AE)
                                    },
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
