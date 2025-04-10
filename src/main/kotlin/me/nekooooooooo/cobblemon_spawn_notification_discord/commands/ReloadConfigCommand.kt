package me.nekooooooooo.cobblemon_spawn_notification_discord.commands

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.literal
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import net.minecraft.text.Text

object ReloadConfigCommand {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                literal("csn-discord")
                    .requires { it.hasPermissionLevel(2) }
                    .then(literal("reload").executes { ctx ->
                        try {
                            ConfigManager.loadConfig()
                            ctx.source.sendFeedback({ Text.literal("CSN Discord Integration config reloaded successfully.") }, false)
                            1
                        } catch (e: Exception) {
                            ctx.source.sendError(Text.literal("Failed to reload CSN Discord Integration config. Check logs."))
                            e.printStackTrace()
                            0
                        }
                    })
            )
        }
    }
}