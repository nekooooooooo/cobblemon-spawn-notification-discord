package me.nekooooooooo.csn_discord.utils

import fr.denisd3d.mc2discord.core.MessageManager
import fr.denisd3d.mc2discord.shadow.discord4j.discordjson.possible.Possible

object Mc2DiscordCompat {
    fun send(message: String) {
        MessageManager.sendMessage(
            listOf("cobblemon_spawn_notification_discord"), // Replace with your mod ID
            message,
            Possible.of("Cobblemon Spawn Notification"),
            Possible.of("https://cdn.modrinth.com/data/LPuJjiQz/a9ef7af8ee170f39131c1b3e7b92237fa07adc56.png")
        ).subscribe()
    }
}