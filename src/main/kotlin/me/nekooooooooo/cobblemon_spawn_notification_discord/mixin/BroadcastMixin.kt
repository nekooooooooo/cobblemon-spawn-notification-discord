package me.nekooooooooo.cobblemon_spawn_notification_discord.mixin

import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import us.timinc.mc.cobblemon.spawnnotification.util.Broadcast
import me.nekooooooooo.cobblemon_spawn_notification_discord.CSNDiscord
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.DcIntegrationCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.DiscordMCChatCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.FabricordCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.Mc2DiscordCompat
import net.minecraft.server.world.ServerWorld
import org.slf4j.Logger

@Mixin(Broadcast::class)
class BroadcastMixin {
    private val LOGGER: Logger = CSNDiscord.LOGGER

    @Inject(method = ["broadcastMessage(Lnet/minecraft/class_2561;)V"], at = [At("TAIL")])
    private fun sendToDiscordCrossDimension(message: Text, ci: CallbackInfo) {
        sendToDiscord(message)
    }

    @Inject(method = ["broadcastMessage(Lnet/minecraft/class_3218;Lnet/minecraft/class_2561;)V"], at = [At("TAIL")])
    private fun sendToDiscordWorld(level: ServerWorld, message: Text, ci: CallbackInfo) {
        if(ConfigManager.CONFIG.bypassCrossDimension)
            sendToDiscord(message)
    }

    private fun sendToDiscord(message: Text) {
        val sanitized = sanitizeMessage(message.string)

        // TODO: Refactor for scalability
        when {
            CSNDiscord.hasDCIntegration -> {
                DcIntegrationCompat.send(sanitized)
                LOGGER.info("Message sent to Discord via DC Integration: $sanitized")
            }

            CSNDiscord.hasMC2Discord -> {
                Mc2DiscordCompat.send(sanitized)
                LOGGER.info("Message sent to Discord via MC2Discord: $sanitized")
            }

            CSNDiscord.hasDMCC -> {
                DiscordMCChatCompat.send(sanitized)
                LOGGER.info("Message sent to Discord via DMCC: $sanitized")
            }

            CSNDiscord.hasFabricord -> {
                FabricordCompat.send(sanitized)
                LOGGER.info("Message sent to Discord via Fabricord: $sanitized")
            }

            else -> {
                LOGGER.warn("No compatible Discord integration mod found!")
            }
        }
    }

    private fun sanitizeMessage(text: String): String {
        return text
            .lines()
            .filterNot { line ->
                val trimmed = line.trim()
                trimmed.isEmpty() ||
                        Regex("^[a-z0-9_\\-]+:[^\\s]*$", RegexOption.IGNORE_CASE).matches(trimmed) ||
                        Regex("^[a-z0-9_\\-]+(:[^\\s]+){1,}$", RegexOption.IGNORE_CASE).matches(trimmed)
            }
            .joinToString("\n")
            .replace(Regex("\\b[a-z0-9_\\-]+:[a-zA-Z0-9_\\-:.]*\\b", RegexOption.IGNORE_CASE), "")
            .replace(Regex("\\s{2,}"), " ")
            .trim()
    }
}
