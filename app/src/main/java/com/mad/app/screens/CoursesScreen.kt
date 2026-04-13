package com.mad.app.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mad.app.navigation.Screen
import com.mad.app.navigation.navigateToTopLevel
import com.mad.app.ui.components.*
import com.mad.app.ui.theme.*
import kotlinx.coroutines.launch

data class Course(val name: String, val description: String, val imageUrl: String)

@Composable
fun CoursesScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) { isVisible = true }

    val courses = listOf(
        Course("Generative AI", "Master LLMs, GANs and diffusion models.", "https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=400"),
        Course("Full Stack", "End-to-end web app development.", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=400"),
        Course("MAD", "Modern Android & iOS application development.", "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?q=80&w=400"),
        Course("NNDL", "Neural Networks and Deep Learning fundamentals.", "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?q=80&w=400"),
        Course("NLP", "Natural Language Processing and text analytics.", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?q=80&w=400"),
        Course("Cloud Computing", "AWS, Azure and scalable infrastructure.", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=80&w=400"),
        Course("Machine Learning", "Classical ML algorithms and statistics.", "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?q=80&w=400"),
        Course("Data Mining", "Extracting patterns from large datasets.", "https://images.unsplash.com/photo-1551288049-bbbda536639a?q=80&w=400"),
        Course("DevOps", "CI/CD pipelines and containerization.", "https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?q=80&w=400"),
        Course("OOSE", "Object Oriented Software Engineering.", "https://images.unsplash.com/photo-1587620962725-abab7fe55159?q=80&w=400")
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigateToTopLevel(Screen.Contact.route) },
                containerColor = AI_Cyan,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Email, contentDescription = "Contact")
            }
        },
        containerColor = DeepBlue
    ) { padding ->
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + scaleIn(),
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "All Courses",
                    style = MaterialTheme.typography.displaySmall,
                    color = AI_Cyan,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(courses) { course ->
                        CourseCard(
                            title = course.name,
                            description = course.description,
                            imageUrl = course.imageUrl,
                            onLearnClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Opening ${course.name} Course...")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
