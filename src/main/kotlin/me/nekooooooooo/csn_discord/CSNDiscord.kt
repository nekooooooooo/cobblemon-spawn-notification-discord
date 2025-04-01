package me.nekooooooooo.csn_discord

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CSNDiscord : ModInitializer {
    private val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")

    override fun onInitialize() {
        LOGGER.info("Cobblemon Spawn Notification: Discord Integration has been initialized!")
    }
}
