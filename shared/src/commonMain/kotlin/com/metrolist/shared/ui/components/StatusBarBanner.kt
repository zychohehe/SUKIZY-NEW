package com.metrolist.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * Theme-coloured gradient behind the status bar. [scrollFraction] returns 0 at the top of the list and 1 once the
 * banner has scrolled away; it is read while drawing, so scrolling never recomposes the banner.
 */
@Composable
fun StatusBarBanner(
    scrollFraction: () -> Float,
    modifier: Modifier = Modifier,
) {
    val accent = MaterialTheme.colorScheme.primary
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 200.dp)
                .graphicsLayer { alpha = (1f - scrollFraction()).coerceIn(0f, 1f) }
                .background(
                    Brush.verticalGradient(
                        listOf(accent.copy(alpha = 0.34f), accent.copy(alpha = 0.12f), Color.Transparent),
                    ),
                ),
    )
}
