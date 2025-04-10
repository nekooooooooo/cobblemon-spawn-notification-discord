package me.nekooooooooo.cobblemon_spawn_notification_discord

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CSNDiscord : ModInitializer {
    private val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")

    companion object {
        var hasDCIntegration: Boolean = false
        var hasMC2Discord: Boolean = false
        var bypassCrossDimension: Boolean = false
    }

    override fun onInitialize() {
        LOGGER.info("Cobblemon Spawn Notification: Discord Integration has been initialized!")

        // TODO: Refactor for scalability
        hasDCIntegration = FabricLoader.getInstance().isModLoaded("dcintegration-fabric")
        hasMC2Discord = FabricLoader.getInstance().isModLoaded("mc2discord")
        if(hasDCIntegration) LOGGER.info("Cobblemon Spawn Notification: DCIntegration detected!")
        if(hasMC2Discord) LOGGER.info("Cobblemon Spawn Notification: MC2Discord detected!")
        if(!hasMC2Discord && !hasDCIntegration) LOGGER.warn("No discord integration mod detected!")
    }
}
