package me.nekooooooooo.cobblemon_spawn_notification_discord

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import me.nekooooooooo.cobblemon_spawn_notification_discord.commands.ReloadConfigCommand

class CSNDiscord : ModInitializer {
    // TODO: Add comments for future me

    companion object {
        var hasDCIntegration: Boolean = false
        var hasMC2Discord: Boolean = false
        var hasDMCC: Boolean = false
        val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")
    }

    override fun onInitialize() {
        LOGGER.info("Cobblemon Spawn Notification: Discord Integration has been initialized!")

        // TODO: Refactor for scalability
        hasDCIntegration = FabricLoader.getInstance().isModLoaded("dcintegration-fabric")
        hasMC2Discord = FabricLoader.getInstance().isModLoaded("mc2discord")
        hasDMCC = FabricLoader.getInstance().isModLoaded("discord-mc-chat")

        if(hasDCIntegration)
            LOGGER.info("Cobblemon Spawn Notification Discord Integration: DCIntegration detected!")
        if(hasMC2Discord)
            LOGGER.info("Cobblemon Spawn Notification Discord Integration: MC2Discord detected!")
        if(hasDMCC)
            LOGGER.info("Cobblemon Spawn Notification Discord Integration: DMCC detected!")
        if(!hasMC2Discord && !hasDCIntegration && !hasDMCC)
            LOGGER.warn("No discord integration mod detected!")

        ConfigManager.loadConfig()
        ReloadConfigCommand.register()
    }
}
