package me.nekooooooooo.cobblemon_spawn_notification_discord.mixin

import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import us.timinc.mc.cobblemon.spawnnotification.util.Broadcast
import me.nekooooooooo.cobblemon_spawn_notification_discord.CSNDiscord
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.DcIntegrationCompat
import me.nekooooooooo.cobblemon_spawn_notification_discord.utils.Mc2DiscordCompat
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Mixin(Broadcast::class)
class BroadcastMixin {
    private val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")

    @Inject(method = ["broadcastMessage(Lnet/minecraft/text/Text;)V"], at = [At("TAIL")])
    private fun sendToDiscordCrossDimension(message: Text, ci: CallbackInfo) {
        when {
            CSNDiscord.hasDCIntegration -> {
                DcIntegrationCompat.send(message.string)
                LOGGER.info("Message sent to Discord via DC Integration: ${message.string}")
            }

            CSNDiscord.hasMC2Discord -> {
                Mc2DiscordCompat.send(message.string)
                LOGGER.info("Message sent to Discord via MC2Discord: ${message.string}")
            }

            else -> {
                LOGGER.warn("No compatible Discord integration mod found!")
            }
        }
    }

//    @Inject(method = ["broadcastMessage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/text/Text;)V"], at = [At("TAIL")])
//    private fun sendToDiscordWorld(level: ServerWorld, message: Text, ci: CallbackInfo) {
//        val discordIntegration = DiscordIntegration.INSTANCE ?: return
//        discordIntegration.sendMessage(message.string)
//    }
}
