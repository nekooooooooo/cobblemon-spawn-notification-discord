package me.nekooooooooo.cobblemon_spawn_notification_discord.utils

import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import me.nekooooooooo.cobblemon_spawn_notification_discord.config.ConfigManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DiscordMCChatCompat {
    private val FALLBACK_LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord-DMCCCompat")

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
        val webhookUrl = resolveWebhookUrl() ?: run {
            FALLBACK_LOGGER.warn("Discord-MC-Chat detected but could not resolve webhook URL via reflection")
            return
        }

        val request = Request.Builder()
            .url(webhookUrl)
            .post(body)
            .build()

        val httpClient = resolveHttpClient() ?: OkHttpClient()
        val logger = resolveLogger()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logger.warn("Webhook message failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    logger.warn("Webhook message failed: ${response.code} - ${response.message}")
                }
            }
        })
    }

    private fun resolveDmccMainClass(): Class<*>? {
        return try {
            Class.forName("com.xujiayao.discord_mc_chat.Main")
        } catch (_: ClassNotFoundException) {
            null
        }
    }

    private fun resolveWebhookUrl(): String? {
        val mainClass = resolveDmccMainClass() ?: return null
        val webhook = try {
            mainClass.getField("WEBHOOK").get(null)
        } catch (_: Throwable) {
            return null
        }

        // DMCC's WEBHOOK is usually an object with a `url` field or `getUrl()` method.
        return try {
            val urlField = webhook.javaClass.getField("url")
            (urlField.get(webhook) as? String)?.takeIf { it.isNotBlank() }
        } catch (_: Throwable) {
            try {
                val getUrl = webhook.javaClass.getMethod("getUrl")
                (getUrl.invoke(webhook) as? String)?.takeIf { it.isNotBlank() }
            } catch (_: Throwable) {
                null
            }
        }
    }

    private fun resolveHttpClient(): OkHttpClient? {
        val mainClass = resolveDmccMainClass() ?: return null
        val client = try {
            mainClass.getField("HTTP_CLIENT").get(null)
        } catch (_: Throwable) {
            return null
        }
        return client as? OkHttpClient
    }

    private fun resolveLogger(): Logger {
        val mainClass = resolveDmccMainClass() ?: return FALLBACK_LOGGER
        val logger = try {
            mainClass.getField("LOGGER").get(null)
        } catch (_: Throwable) {
            return FALLBACK_LOGGER
        }
        return logger as? Logger ?: FALLBACK_LOGGER
    }
}