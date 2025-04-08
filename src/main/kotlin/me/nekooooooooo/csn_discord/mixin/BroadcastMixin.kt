package me.nekooooooooo.csn_discord.mixin

import net.minecraft.text.Text
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import us.timinc.mc.cobblemon.spawnnotification.util.Broadcast
import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration

@Mixin(Broadcast::class)
class BroadcastMixin {

    @Inject(method = ["broadcastMessage(Lnet/minecraft/text/Text;)V"], at = [At("TAIL")])
    private fun sendToDiscordCrossDimension(message: Text, ci: CallbackInfo) {
        val discordIntegration = DiscordIntegration.INSTANCE ?: return
        discordIntegration.sendMessage(message.string)
    }

//    @Inject(method = ["broadcastMessage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/text/Text;)V"], at = [At("TAIL")])
//    private fun sendToDiscordWorld(level: ServerWorld, message: Text, ci: CallbackInfo) {
//        val discordIntegration = DiscordIntegration.INSTANCE ?: return
//        discordIntegration.sendMessage(message.string)
//    }
}
