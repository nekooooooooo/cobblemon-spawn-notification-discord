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
        // TODO: Refactor for scalability
        when {
            CSNDiscord.hasDCIntegration -> {
                DcIntegrationCompat.send(message.string)
                LOGGER.info("Message sent to Discord via DC Integration: ${message.string}")
            }

            CSNDiscord.hasMC2Discord -> {
                Mc2DiscordCompat.send(message.string)
                LOGGER.info("Message sent to Discord via MC2Discord: ${message.string}")
            }

            CSNDiscord.hasDMCC -> {
                DiscordMCChatCompat.send(message.string)
                LOGGER.info("Message sent to Discord via DMCC: ${message.string}")
            }

            else -> {
                LOGGER.warn("No compatible Discord integration mod found!")
            }
        }
    }
}
