package com.mad.app.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mad.app.navigation.Screen
import com.mad.app.navigation.navigateToTopLevel
import com.mad.app.ui.components.*
import com.mad.app.ui.theme.*

@Composable
fun AboutScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandVertically()
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
                Text(
                    text = "Our Mission & Vision",
                    style = MaterialTheme.typography.displaySmall,
                    color = AI_Cyan,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "To empower students with the knowledge and skills to lead the AI revolution and build a better future through intelligent systems.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Vision:\nWe envision a world where AI is seamlessly integrated into every aspect of life, enhancing human potential and solving complex global challenges.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            item {
                FeaturedCoursesRow(
                    courses = listOf(
                        Triple(
                            "Generative AI",
                            "Master LLMs and GANs",
                            "https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=400"
                        ),
                        Triple(
                            "Full Stack",
                            "Modern Web Development",
                            "https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=400"
                        )
                    ),
                    onMoreClick = { navController.navigateToTopLevel(Screen.Courses.route) },
                    onLearnClick = { /* Handle learn click */ }
                )
            }

            item {
                CTABanner(
                    title = "Start Your AI Journey Today!",
                    buttonText = "Contact Us",
                    onClick = { navController.navigateToTopLevel(Screen.Contact.route) }
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}
