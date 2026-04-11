package com.mad.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Purple-Teal palette
val Purple80 = Color(0xFFCFBCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Teal80 = Color(0xFF70EFDE)

val Purple40 = Color(0xFF7C4DFF)
val PurpleGrey40 = Color(0xFF625B71)
val Teal40 = Color(0xFF00BFA5)

val PrimaryColor = Color(0xFF7C4DFF)
val SecondaryColor = Color(0xFF00BFA5)
val TertiaryColor = Color(0xFFFF6D00)
val SurfaceColor = Color(0xFFFFFBFE)
val BackgroundColor = Color(0xFFF8F5FF)
val OnPrimaryColor = Color.White
val OnBackgroundColor = Color(0xFF1C1B1F)
val ErrorColor = Color(0xFFB3261E)

val GradientStart = Color(0xFF7C4DFF)
val GradientMid = Color(0xFF536DFE)
val GradientEnd = Color(0xFF00BFA5)

// Task colors
val TaskCO1 = Color(0xFF7C4DFF)
val TaskCO2 = Color(0xFF00BFA5)
val TaskCO3 = Color(0xFFFF6D00)
val TaskCO4 = Color(0xFFE91E63)
val TaskCO5 = Color(0xFF2196F3)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = Teal80,
    tertiary = PurpleGrey80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onPrimary = Color(0xFF381E72),
    onSecondary = Color(0xFF00382E),
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = Teal40,
    tertiary = TertiaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnPrimaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnBackgroundColor,
    error = ErrorColor,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    primaryContainer = Color(0xFFE8DDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondaryContainer = Color(0xFFC8FFF4),
    onSecondaryContainer = Color(0xFF002019),
)

@Composable
fun MADShowcaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MADTypography,
        content = content
    )
}
