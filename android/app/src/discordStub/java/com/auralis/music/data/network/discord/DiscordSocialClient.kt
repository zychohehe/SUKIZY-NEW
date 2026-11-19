package com.auralis.music.data.network.discord

import android.content.Context

/**
 * No-op replacement for the real client, compiled when the proprietary Discord Social SDK
 * is not present. Discord Rich Presence is simply unavailable (initialize() returns false).
 */
object DiscordSocialClient {
    const val APPLICATION_ID = 1553114990909591732L

    data class UserProfile(val username: String, val avatarUrl: String)

    @Volatile var readyListener: (() -> Unit)? = null
    @Volatile var profileListener: ((UserProfile) -> Unit)? = null
    @Volatile var currentProfile: UserProfile? = null
        private set

    @Suppress("UNUSED_PARAMETER")
    fun initialize(context: Context): Boolean = false

    @Suppress("UNUSED_PARAMETER")
    fun updatePresence(p: DiscordPresence) = Unit

    @Suppress("UNUSED_PARAMETER")
    fun setOnlineStatus(status: Int) = Unit

    fun authorize(onResult: (Boolean, String, String) -> Unit) =
        onResult(false, "", "Discord SDK not included in this build")

    fun clearPresence() = Unit
    fun forgetAuthorization() = Unit
    fun pumpCallbacks() = Unit
}
