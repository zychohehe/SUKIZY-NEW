package com.auralis.music.data.network.discord

import android.app.Activity
import com.discord.socialsdk.DiscordSocialSdkInit

/** Real implementation: used when libs/discord_partner_sdk.jar is present. */
object DiscordSdkBridge {
    fun setEngineActivity(activity: Activity) = DiscordSocialSdkInit.setEngineActivity(activity)
}
