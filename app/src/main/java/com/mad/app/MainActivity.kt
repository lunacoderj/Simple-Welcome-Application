package com.mad.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mad.app.ui.home.HomeScreen
import com.mad.app.ui.theme.MADShowcaseTheme
import com.mad.app.ui.welcome.WelcomeScreen
import com.mad.app.ui.toast.ToastInteractionScreen
import com.mad.app.ui.components.UIComponentsScreen
import com.mad.app.ui.navigation.ActivityNavigationScreen
import com.mad.app.ui.datapassing.DataPassingScreen
import com.mad.app.ui.debugging.DebuggingScreen
import com.mad.app.ui.fragments.FragmentIntegrationLauncher
import com.mad.app.ui.recyclerview.RecyclerViewScreen
import com.mad.app.ui.implicit.ImplicitIntentScreen
import com.mad.app.ui.api.APIFetchScreen
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.d("MainActivity created")

        setContent {
            MADShowcaseTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf("home") }

                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            if (targetState == "home") {
                                (slideInHorizontally { -it } + fadeIn()) togetherWith
                                        (slideOutHorizontally { it } + fadeOut())
                            } else {
                                (slideInHorizontally { it } + fadeIn()) togetherWith
                                        (slideOutHorizontally { -it } + fadeOut())
                            }
                        },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            "home" -> HomeScreen(
                                onTaskClick = { route -> currentScreen = route }
                            )
                            "welcome" -> WelcomeScreen(
                                onBack = { currentScreen = "home" },
                                onAutoNavigate = { currentScreen = "home" }
                            )
                            "toast" -> ToastInteractionScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "components" -> UIComponentsScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "navigation" -> ActivityNavigationScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "datapassing" -> DataPassingScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "debugging" -> DebuggingScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "fragments" -> FragmentIntegrationLauncher(
                                onBack = { currentScreen = "home" }
                            )
                            "recyclerview" -> RecyclerViewScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "implicit" -> ImplicitIntentScreen(
                                onBack = { currentScreen = "home" }
                            )
                            "api" -> APIFetchScreen(
                                onBack = { currentScreen = "home" }
                            )
                        }
                    }
                }
            }
        }
    }
}
