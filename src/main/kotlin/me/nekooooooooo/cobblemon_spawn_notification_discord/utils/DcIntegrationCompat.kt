package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import de.erdbeerbaerlp.dcintegration.common.storage.Configuration

object DcIntegrationCompat {
    fun send(message: String) {
        if(Configuration.instance().webhook.enable) {
            DiscordIntegration.INSTANCE?.sendMessage(
                message,
                ConfigManager.CONFIG.botAvatarUrl,
                ConfigManager.CONFIG.botName
            )
        } else DiscordIntegration.INSTANCE?.sendMessage(message)
    }
}