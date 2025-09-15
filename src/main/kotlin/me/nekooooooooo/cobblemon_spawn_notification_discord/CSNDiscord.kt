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
        val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")

        // still pretty ugly and redundant, will refactor again in the future
        private val supportedDiscordMods = listOf(
            "dcintegration-fabric",
            "mc2discord",
            "discord-mc-chat",
            "fabricord"
        )

        val loadedDiscordMods = mutableListOf<String>()

        var hasDCIntegration = false
        var hasMC2Discord = false
        var hasDMCC = false
        var hasFabricord = false
    }

    override fun onInitialize() {
        LOGGER.info("Cobblemon Spawn Notification: Discord Integration has been initialized!")

        detectDiscordMods()

        ConfigManager.loadConfig()
        ReloadConfigCommand.register()
    }

    private fun detectDiscordMods() {
        val loader = FabricLoader.getInstance()

        // still pretty ugly and redundant, will refactor again in the future
        supportedDiscordMods.forEach { modId ->
            if (loader.isModLoaded(modId)) {
                loadedDiscordMods.add(modId)
                when (modId) {
                    "dcintegration-fabric" -> {
                        hasDCIntegration = true
                        LOGGER.info("Cobblemon Spawn Notification: Discord Integration detected!")
                    }
                    "mc2discord" -> {
                        hasMC2Discord = true
                        LOGGER.info("Cobblemon Spawn Notification: MC2Discord detected!")
                    }
                    "discord-mc-chat" -> {
                        hasDMCC = true
                        LOGGER.info("Cobblemon Spawn Notification: Discord-MC-Chat detected!")
                    }
                    "fabricord" -> {
                        hasFabricord = true
                        LOGGER.info("Cobblemon Spawn Notification: Fabricord detected!")
                    }
                    else -> {
                        LOGGER.info("Cobblemon Spawn Notification: $modId detected!")
                    }
                }
            }
        }

        if (loadedDiscordMods.isEmpty()) {
            LOGGER.warn("Cobblemon Spawn Notification: No supported Discord integration mods detected!")
        }
    }

}
