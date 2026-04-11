package com.mad.app.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val coLabel: String,
    val icon: ImageVector,
    val accentColor: Color,
    val relevance: Float, // 0.0 to 1.0
    val route: String
)

object TaskRepository {
    fun getTasks(): List<TaskItem> = listOf(
        TaskItem(
            id = 1,
            title = "Welcome Screen",
            description = "Animated intro with Compose",
            coLabel = "CO1",
            icon = Icons.Filled.EmojiPeople,
            accentColor = Color(0xFF7C4DFF),
            relevance = 0.7f,
            route = "welcome"
        ),
        TaskItem(
            id = 2,
            title = "Toast Interaction",
            description = "Custom snackbar & haptics",
            coLabel = "CO2",
            icon = Icons.Filled.TouchApp,
            accentColor = Color(0xFF00BFA5),
            relevance = 0.8f,
            route = "toast"
        ),
        TaskItem(
            id = 3,
            title = "UI Components",
            description = "Material 3 form validation",
            coLabel = "CO2",
            icon = Icons.Filled.Widgets,
            accentColor = Color(0xFF00BFA5),
            relevance = 0.9f,
            route = "components"
        ),
        TaskItem(
            id = 4,
            title = "Activity Navigation",
            description = "Scene transitions & intents",
            coLabel = "CO3",
            icon = Icons.Filled.Navigation,
            accentColor = Color(0xFFFF6D00),
            relevance = 0.85f,
            route = "navigation"
        ),
        TaskItem(
            id = 5,
            title = "Data Passing",
            description = "Login flow with DataStore",
            coLabel = "CO3",
            icon = Icons.Filled.SwapHoriz,
            accentColor = Color(0xFFFF6D00),
            relevance = 0.9f,
            route = "datapassing"
        ),
        TaskItem(
            id = 6,
            title = "Debugging",
            description = "Crash reports & Timber logging",
            coLabel = "CO3",
            icon = Icons.Filled.BugReport,
            accentColor = Color(0xFFFF6D00),
            relevance = 0.75f,
            route = "debugging"
        ),
        TaskItem(
            id = 7,
            title = "Fragment Integration",
            description = "Bottom nav with fragments",
            coLabel = "CO4",
            icon = Icons.Filled.ViewModule,
            accentColor = Color(0xFFE91E63),
            relevance = 0.85f,
            route = "fragments"
        ),
        TaskItem(
            id = 8,
            title = "RecyclerView",
            description = "DiffUtil, swipe & search",
            coLabel = "CO4",
            icon = Icons.Filled.ViewList,
            accentColor = Color(0xFFE91E63),
            relevance = 0.95f,
            route = "recyclerview"
        ),
        TaskItem(
            id = 9,
            title = "Implicit Intents",
            description = "System intent actions",
            coLabel = "CO5",
            icon = Icons.Filled.Share,
            accentColor = Color(0xFF2196F3),
            relevance = 0.8f,
            route = "implicit"
        ),
        TaskItem(
            id = 10,
            title = "API Fetch",
            description = "Retrofit + Coroutines",
            coLabel = "CO5",
            icon = Icons.Filled.Cloud,
            accentColor = Color(0xFF2196F3),
            relevance = 0.95f,
            route = "api"
        )
    )
}
