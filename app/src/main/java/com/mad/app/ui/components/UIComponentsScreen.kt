package com.mad.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mad.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIComponentsScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var submittedText by remember { mutableStateOf("") }
    var displayedSubmit by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var shakeOffset by remember { mutableStateOf(0f) }
    var isSubmitted by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val isFormValid = name.isNotBlank() && email.contains("@") && message.length >= 5

    // Shake animation
    val shakeAnim = remember { Animatable(0f) }

    fun triggerShake() {
        scope.launch {
            shakeAnim.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    0f at 0
                    -15f at 50
                    15f at 100
                    -12f at 150
                    12f at 200
                    -8f at 250
                    8f at 300
                    -4f at 350
                    0f at 400
                }
            )
        }
    }

    // Typewriter for submitted text
    LaunchedEffect(submittedText) {
        if (submittedText.isNotEmpty()) {
            displayedSubmit = ""
            for (i in submittedText.indices) {
                displayedSubmit = submittedText.substring(0, i + 1)
                delay(30)
            }
        }
    }

    // Submit button animation
    val buttonScale by animateFloatAsState(
        targetValue = if (isFormValid) 1f else 0.95f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (isFormValid) 1f else 0.5f,
        animationSpec = tween(300),
        label = "buttonAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("UI Components", fontWeight = FontWeight.SemiBold) },
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                "Contact Form",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Material 3 components with validation",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    showError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = shakeAnim.value.dp),
                label = { Text("Full Name") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = showError && name.isBlank(),
                supportingText = {
                    if (showError && name.isBlank()) {
                        Text("Name is required", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email Field with character counter
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = shakeAnim.value.dp),
                label = { Text("Email Address") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = showError && !email.contains("@"),
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (showError && !email.contains("@")) {
                            Text("Invalid email", color = MaterialTheme.colorScheme.error)
                        } else {
                            Spacer(modifier = Modifier)
                        }
                        AnimatedVisibility(
                            visible = email.isNotEmpty(),
                            enter = fadeIn(tween(200)),
                            exit = fadeOut(tween(200))
                        ) {
                            Text(
                                "${email.length} chars",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Message Field
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                    showError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .offset(x = shakeAnim.value.dp),
                label = { Text("Message") },
                shape = RoundedCornerShape(12.dp),
                maxLines = 4,
                isError = showError && message.length < 5,
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (showError && message.length < 5) {
                            Text(
                                "Min 5 characters",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Spacer(modifier = Modifier)
                        }
                        Text(
                            "${message.length}/500",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (message.length > 500)
                                MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button with animated state
            Button(
                onClick = {
                    if (isFormValid) {
                        isSubmitted = true
                        submittedText = "From: $name\nEmail: $email\n\n$message"
                        showError = false
                    } else {
                        showError = true
                        triggerShake()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                shape = RoundedCornerShape(16.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) PrimaryColor
                    else PrimaryColor.copy(alpha = 0.4f)
                )
            ) {
                AnimatedContent(
                    targetState = isSubmitted,
                    transitionSpec = {
                        scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut()
                    },
                    label = "buttonContent"
                ) { submitted ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (submitted) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submitted!", fontWeight = FontWeight.SemiBold)
                        } else {
                            Icon(
                                Icons.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit Form", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display submitted text with typewriter animation
            AnimatedVisibility(
                visible = displayedSubmit.isNotEmpty(),
                enter = fadeIn(tween(300)) + expandVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SecondaryColor.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "📨 Submitted Data",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SecondaryColor
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = displayedSubmit,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}
