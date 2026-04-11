package com.mad.app.ui.toast

import android.os.VibrationEffect
import android.os.Vibrator
import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToastInteractionScreen(onBack: () -> Unit) {
    var clickCount by remember { mutableIntStateOf(0) }
    var showSnackbar by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Confetti particles
    val particles = remember { mutableStateListOf<ConfettiParticle>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Top Bar
            TopAppBar(
                title = {
                    Text("Toast Interaction", fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Click count badge with CountUp animation
                val animatedCount by animateIntAsState(
                    targetValue = clickCount,
                    animationSpec = tween(durationMillis = 300),
                    label = "count"
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(GradientStart, GradientEnd)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.TouchApp,
                                contentDescription = "Touch",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Interaction Counter",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Animated counter
                        AnimatedContent(
                            targetState = animatedCount,
                            transitionSpec = {
                                (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                                        (scaleOut(targetScale = 1.2f) + fadeOut())
                            },
                            label = "counter"
                        ) { count ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PrimaryColor.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = "$count",
                                    modifier = Modifier.padding(
                                        horizontal = 24.dp,
                                        vertical = 8.dp
                                    ),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "taps recorded",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Interactive button with scale animation
                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressed by interactionSource.collectIsPressedAsState()
                        val buttonScale by animateFloatAsState(
                            targetValue = if (isPressed) 0.95f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "buttonScale"
                        )

                        Button(
                            onClick = {
                                clickCount++
                                showSnackbar = true
                                scope.launch {
                                    delay(2000)
                                    showSnackbar = false
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .scale(buttonScale),
                            shape = RoundedCornerShape(16.dp),
                            interactionSource = interactionSource,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryColor
                            )
                        ) {
                            Text(
                                "Tap Me! 🎯",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Long press button
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            // Haptic feedback
                                            val vibrator =
                                                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                            vibrator.vibrate(
                                                VibrationEffect.createOneShot(
                                                    50,
                                                    VibrationEffect.DEFAULT_AMPLITUDE
                                                )
                                            )
                                            // Trigger confetti
                                            showConfetti = true
                                            repeat(50) {
                                                particles.add(
                                                    ConfettiParticle(
                                                        x = 540f + Random.nextFloat() * 200 - 100,
                                                        y = 800f,
                                                        velocityX = Random.nextFloat() * 12 - 6,
                                                        velocityY = -(Random.nextFloat() * 15 + 5),
                                                        color = listOf(
                                                            Color(0xFF7C4DFF),
                                                            Color(0xFF00BFA5),
                                                            Color(0xFFFF6D00),
                                                            Color(0xFFE91E63),
                                                            Color(0xFF2196F3),
                                                            Color(0xFFFFD600)
                                                        ).random(),
                                                        size = Random.nextFloat() * 8 + 4,
                                                        life = 1f
                                                    )
                                                )
                                            }
                                            scope.launch {
                                                delay(3000)
                                                showConfetti = false
                                                particles.clear()
                                            }
                                        }
                                    )
                                },
                            shape = RoundedCornerShape(16.dp),
                            border = ButtonDefaults.outlinedButtonBorder
                        ) {
                            Text(
                                "Long Press for Confetti 🎊",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Long press the button above for haptic feedback + confetti",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Custom Snackbar
        AnimatedVisibility(
            visible = showSnackbar,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                initialOffsetY = { it }
            ) + fadeIn(),
            exit = slideOutVertically(
                animationSpec = tween(200),
                targetOffsetY = { it }
            ) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
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
                        text = "🎉",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Tap #$clickCount recorded!",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(
                            "Custom animated snackbar",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                    TextButton(onClick = { showSnackbar = false }) {
                        Text("DISMISS", color = SecondaryColor)
                    }
                }
            }
        }

        // Confetti Canvas
        if (showConfetti) {
            var tick by remember { mutableIntStateOf(0) }
            LaunchedEffect(showConfetti) {
                while (showConfetti) {
                    delay(16)
                    tick++
                    particles.forEach { p ->
                        p.x += p.velocityX
                        p.y += p.velocityY
                        p.velocityY += 0.3f // gravity
                        p.life -= 0.008f
                    }
                    particles.removeAll { it.life <= 0 }
                }
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                particles.forEach { p ->
                    drawCircle(
                        color = p.color.copy(alpha = p.life.coerceIn(0f, 1f)),
                        radius = p.size,
                        center = androidx.compose.ui.geometry.Offset(p.x, p.y)
                    )
                }
            }
        }
    }
}

data class ConfettiParticle(
    var x: Float,
    var y: Float,
    var velocityX: Float,
    var velocityY: Float,
    val color: Color,
    val size: Float,
    var life: Float
)
