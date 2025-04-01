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

    @Inject(method = ["broadcastMessage"], at = [At("TAIL")])
    private fun sendToDiscord(message: Text, ci: CallbackInfo) {
        val discordIntegration = DiscordIntegration.INSTANCE ?: return
        discordIntegration.sendMessage(message.string)
    }
}
