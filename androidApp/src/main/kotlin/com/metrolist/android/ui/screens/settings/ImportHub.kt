/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.android.ui.screens.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/** Satu platform sumber import (Spotify, Apple Music, dst.) yang ditampilkan sebagai kartu di halaman Import. */
data class ImportPlatform(
    val title: String,
    val description: String,
    val accent: Color,
    val monogram: String,
    val enabled: Boolean = true,
    val onClick: () -> Unit,
)

/**
 * Tampilan modern halaman Import: hero bergradasi + daftar sumber dalam satu kartu besar,
 * setiap baris punya badge brand, deskripsi, dan panah.
 */
@Composable
fun ImportPlatformGrid(
    platforms: List<ImportPlatform>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ImportHero(platforms)
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
        ) {
            platforms.forEachIndexed { index, platform ->
                ImportPlatformRow(platform)
                if (index != platforms.lastIndex) {
                    Box(
                        Modifier
                            .padding(start = 76.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)),
                    )
                }
            }
        }
    }
}

@Composable
private fun ImportHero(platforms: List<ImportPlatform>) {
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.surfaceContainerHigh
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            primary.copy(alpha = 0.32f).compositeOverSurface(surface),
                            surface,
                        ),
                    ),
                ).padding(22.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Impor Playlist",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "Pindahkan koleksi musikmu dari layanan lain. Pilih sumber di bawah, sisanya otomatis.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy((-8).dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp),
            ) {
                platforms.take(6).forEach { platform ->
                    Box(
                        modifier =
                            Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(platform.accent)
                                .border(2.dp, surface, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = platform.monogram,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
                Text(
                    text = "  ${platforms.size} sumber tersedia",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun ImportPlatformRow(
    platform: ImportPlatform,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 700f),
        label = "importRowScale",
    )
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }.alpha(if (platform.enabled) 1f else 0.5f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    enabled = platform.enabled,
                    onClick = platform.onClick,
                ).padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.linearGradient(listOf(platform.accent, platform.accent.copy(alpha = 0.72f)))),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = platform.monogram,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = platform.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = platform.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = "›",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun Color.compositeOverSurface(background: Color): Color {
    val a = alpha
    return Color(
        red = red * a + background.red * (1f - a),
        green = green * a + background.green * (1f - a),
        blue = blue * a + background.blue * (1f - a),
        alpha = 1f,
    )
}
