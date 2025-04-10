package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import fr.denisd3d.mc2discord.core.MessageManager
import fr.denisd3d.mc2discord.shadow.discord4j.discordjson.possible.Possible
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager

object Mc2DiscordCompat {
    fun send(message: String) {
        MessageManager.sendMessage(
            listOf("cobblemon_spawn_notification_discord"),
            message,
            Possible.of(ConfigManager.CONFIG.botName),
            Possible.of(ConfigManager.CONFIG.botAvatarUrl)
        ).subscribe()
    }
}