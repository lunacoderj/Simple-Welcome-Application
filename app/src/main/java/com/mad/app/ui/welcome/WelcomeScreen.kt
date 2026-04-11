package com.mad.app.ui.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.app.ui.theme.GradientEnd
import com.mad.app.ui.theme.GradientMid
import com.mad.app.ui.theme.GradientStart
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onBack: () -> Unit,
    onAutoNavigate: () -> Unit
) {
    val fullText = "Welcome to Android"
    var displayedText by remember { mutableStateOf("") }
    var showSubtitle by remember { mutableStateOf(false) }
    var showCard by remember { mutableStateOf(false) }
    var robotVisible by remember { mutableStateOf(false) }

    // Typewriter effect
    LaunchedEffect(Unit) {
        delay(300)
        robotVisible = true
        delay(500)
        for (i in fullText.indices) {
            displayedText = fullText.substring(0, i + 1)
            delay(80)
        }
        delay(300)
        showSubtitle = true
        delay(200)
        showCard = true

        // Auto-navigate after 2.5s
        delay(2500)
        onAutoNavigate()
    }

    // Shimmer animation
    val shimmerTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by shimmerTransition.animateFloat(
        initialValue = -500f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    // Robot bounce animation
    val robotBounce by shimmerTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_bounce"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientMid,
                        GradientEnd
                    )
                )
            )
    ) {
        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = 40.dp, start = 8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Android Robot Emoji Animation
            AnimatedVisibility(
                visible = robotVisible,
                enter = scaleIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) + fadeIn()
            ) {
                Text(
                    text = "🤖",
                    fontSize = 100.sp,
                    modifier = Modifier.offset(y = robotBounce.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Typewriter text with shimmer
            Text(
                text = displayedText,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.copy(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.7f),
                            Color(0xFFE0E0E0),
                            Color.White
                        ),
                        start = Offset(shimmerOffset, 0f),
                        end = Offset(shimmerOffset + 400f, 0f)
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(500)) + slideInVertically(
                    tween(500),
                    initialOffsetY = { it / 2 }
                )
            ) {
                Text(
                    text = "Your journey into Modern Android Development starts here",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Feature card
            AnimatedVisibility(
                visible = showCard,
                enter = fadeIn(tween(600)) + scaleIn(
                    tween(600),
                    initialScale = 0.85f
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✨ 10 MAD Tasks • Jetpack Compose • MVVM",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Progress dots
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            repeat(10) { idx ->
                                var dotVisible by remember { mutableStateOf(false) }
                                LaunchedEffect(idx) {
                                    delay(idx * 100L)
                                    dotVisible = true
                                }
                                AnimatedVisibility(
                                    visible = dotVisible,
                                    enter = scaleIn(spring(dampingRatio = 0.4f))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(
                                                Color.White.copy(alpha = 0.7f),
                                                RoundedCornerShape(4.dp)
                                            )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Auto-navigating to dashboard...",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
