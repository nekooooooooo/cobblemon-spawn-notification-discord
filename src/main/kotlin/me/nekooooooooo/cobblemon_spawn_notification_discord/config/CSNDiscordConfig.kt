package me.nekooooooooo.cobblemon_spawn_notification_discord.config

import kotlinx.serialization.Serializable

@Serializable
data class CSNDiscordConfig(
    val bypassCrossDimension: Boolean,
    val botName: String,
    val botAvatarUrl: String
)