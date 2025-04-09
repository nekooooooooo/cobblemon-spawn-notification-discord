package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration

object DcIntegrationCompat {
    fun send(message: String) {
        DiscordIntegration.INSTANCE?.sendMessage(message)
    }
}