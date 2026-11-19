package com.auralis.music.data.network.discord

/** One Rich Presence update with every field already chosen; an empty string leaves that field out. */
data class DiscordPresence(
    val name: String,
    val details: String,
    val state: String,
    val largeImage: String,
    val largeText: String,
    val smallImage: String,
    val smallText: String,
    val activityType: String,
    val isPlaying: Boolean,
    val positionMs: Long,
    val durationMs: Long,
    val showWhenPaused: Boolean
)
