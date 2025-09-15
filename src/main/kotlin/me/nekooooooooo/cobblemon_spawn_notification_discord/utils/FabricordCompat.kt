package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import net.ririfa.fabricord.discord.DiscordBotManager

object FabricordCompat {
    fun send(message: String) {
        DiscordBotManager.sendToDiscord(message)
    }
}