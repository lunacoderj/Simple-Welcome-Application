package com.mad.app.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mad.app.navigation.Screen
import com.mad.app.navigation.navigateToTopLevel
import com.mad.app.ui.components.*
import com.mad.app.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepBlue)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column {
                    Text(
                        text = "Good Morning,",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Text(
                        text = "AI & ML Student 🎓",
                        style = MaterialTheme.typography.displaySmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(end = 20.dp)
                ) {
                    val items = listOf("Future of AI", "ML Labs", "Neural Networks")
                    items(items) { title ->
                        HeroCard(title)
                    }
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "About Our Department",
                        style = MaterialTheme.typography.titleLarge,
                        color = AI_Cyan
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Our department focuses on cutting-edge research and education in Artificial Intelligence and Machine Learning.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { navController.navigateToTopLevel(Screen.About.route) },
                        border = androidx.compose.foundation.BorderStroke(1.dp, AI_Purple),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Learn More", color = AI_Purple)
                    }
                }
            }

            item {
                FeaturedCoursesRow(
                    courses = listOf(
                        Triple("Generative AI", "Master LLMs and GANs", "https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=400"),
                        Triple("Full Stack", "Modern Web Development", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=400")
                    ),
                    onMoreClick = { navController.navigateToTopLevel(Screen.Courses.route) },
                    onLearnClick = { /* Handle learn click */ }
                )
            }

            item {
                CTABanner(
                    title = "Ready to Join the AI Revolution?",
                    buttonText = "Contact Us Now",
                    onClick = { navController.navigateToTopLevel(Screen.Contact.route) }
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun HeroCard(title: String) {
    Box(
        modifier = Modifier
            .size(width = 280.dp, height = 160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(AI_Purple.copy(alpha = 0.3f))
            .padding(24.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Explore the possibilities",
                style = MaterialTheme.typography.labelMedium,
                color = AI_Cyan
            )
        }
    }
}
