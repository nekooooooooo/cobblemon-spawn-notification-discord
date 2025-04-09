package me.nekooooooooo.csn_discord.utils

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration

object DcIntegrationCompat {
    fun send(message: String) {
        DiscordIntegration.INSTANCE?.sendMessage(message)
    }
}