package me.nekooooooooo.cobblemon_spawn_notification_discord.mixin

import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import us.timinc.mc.cobblemon.spawnnotification.destination.Chat
import me.nekooooooooo.cobblemon_spawn_notification_discord.CSNDiscord
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.DcIntegrationCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.DiscordMCChatCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.FabricordCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.Mc2DiscordCompat
import org.slf4j.Logger
import us.timinc.mc.cobblemon.spawnnotification.SpawnNotification
import us.timinc.mc.cobblemon.spawnnotification.context.BroadcastContext

@Mixin(Chat::class)
abstract class BroadcastMixin {
    private val LOGGER: Logger = CSNDiscord.LOGGER

/*
    @Inject(method = ["broadcastMessage(Lnet/minecraft/class_2561;)V"], at = [At("TAIL")])
    private fun sendToDiscordCrossDimension(message: Text, ci: CallbackInfo) {
        sendToDiscord(message)
    }

    @Inject(method = ["broadcastMessage(Lnet/minecraft/class_3218;Lnet/minecraft/class_2561;)V"], at = [At("TAIL")])
    private fun sendToDiscordWorld(level: ServerWorld, message: Text, ci: CallbackInfo) {
        if(ConfigManager.CONFIG.bypassCrossDimension)
            sendToDiscord(message)
    }

    @Inject(method = ["broadcast(Lnet/minecraft/class_3218;Lnet/minecraft/class_2561;)V"], at = [At("TAIL")])
    private fun sendToDiscordWorld(level: ServerWorld, message: Text, ci: CallbackInfo) {
        if(ConfigManager.CONFIG.bypassCrossDimension)
            sendToDiscord(message)
    }

*/

    @Inject(
        method = ["broadcast"],
        at = [At("TAIL")]
    )
    private fun onBroadcastTail(
        data: Chat.BroadcastData,
        ctx: BroadcastContext,
        ci: CallbackInfo
    ) {
        val parts = data.parts.map {
            SpawnNotification.REGISTRIES.PARTS.get(it)?.getPart(ctx)
                ?: Text.empty()
        }.toTypedArray()

        val message = Text.translatable(data.message, *parts)

        val plainText = Formatting.strip(message.string) ?: ""

        sendToDiscord(plainText)
    }

    private fun sendToDiscord(message: String) {
        val sanitized = sanitizeMessage(message)

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
