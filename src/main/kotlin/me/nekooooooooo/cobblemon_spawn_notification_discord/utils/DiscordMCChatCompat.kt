package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import com.google.gson.JsonObject
import com.xujiayao.discord_mc_chat.Main as DMCCMain
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager

object DiscordMCChatCompat {
    fun send(message: String) {
        sendWebhookMessage(
            message,
            ConfigManager.CONFIG.botName,
            ConfigManager.CONFIG.botAvatarUrl
        )
    }

    private fun sendWebhookMessage(content: String, username: String, avatarUrl: String) {
        val json = JsonObject().apply {
            addProperty("content", content)
            addProperty("username", username)
            addProperty("avatar_url", avatarUrl)
        }

        val body = json.toString().toRequestBody("application/json".toMediaType())
        val webhookUrl = DMCCMain.WEBHOOK.url

        val request = Request.Builder()
            .url(webhookUrl)
            .post(body)
            .build()

        DMCCMain.HTTP_CLIENT.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                DMCCMain.LOGGER.warn("Webhook message failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    DMCCMain.LOGGER.warn("Webhook message failed: ${response.code} - ${response.message}")
                }
            }
        })
    }
}