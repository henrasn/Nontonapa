package com.henrasn.nontonapa.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GentleSky,
    onPrimary = CountsBlack,
    primaryContainer = Seafarer,
    onPrimaryContainer = MorningMist,

    secondary = MediterraneanBlue,
    onSecondary = Brilliance,
    secondaryContainer = Seafarer,
    onSecondaryContainer = MorningMist,

    background = CountsBlack,
    onBackground = Brilliance,

    surface = Seafarer,
    onSurface = MorningMist,
    surfaceVariant = MediterraneanBlue,
    onSurfaceVariant = MorningMist,

    outline = GentleSky,
)

private val LightColorScheme =
    lightColorScheme(
        primary = MediterraneanBlue,
        onPrimary = Brilliance,
        primaryContainer = GentleSky,
        onPrimaryContainer = CountsBlack,

        secondary = Seafarer,
        onSecondary = Brilliance,
        secondaryContainer = MorningMist,
        onSecondaryContainer = Seafarer,

        background = Brilliance,
        onBackground = CountsBlack,

        surface = MorningMist,
        onSurface = CountsBlack,
        surfaceVariant = GentleSky,
        onSurfaceVariant = Seafarer,

        outline = MediterraneanBlue,
    )

@Composable
fun NontonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
