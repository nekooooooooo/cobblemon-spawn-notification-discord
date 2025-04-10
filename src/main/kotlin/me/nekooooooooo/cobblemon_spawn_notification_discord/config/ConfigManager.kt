package me.nekooooooooo.cobblemon_spawn_notification_discord.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

object ConfigManager {

    // Thanks @Gitoido!

    private val LOGGER: Logger = LoggerFactory.getLogger("CSN-Discord")
    private const val CONFIG_PATH: String = "config/csn-discord-integration.json"
    lateinit var CONFIG: CSNDiscordConfig

    @OptIn(ExperimentalSerializationApi::class)
    fun loadConfig() {
        val configFile = File(CONFIG_PATH)
        configFile.parentFile.mkdirs()

        CONFIG = try {
            if (configFile.exists() && configFile.readText().isNotBlank()) {
                LOGGER.info("Loading config for Cobblemon Spawn Notification Discord Integration")
                configFile.inputStream().let {
                    Json.decodeFromStream<CSNDiscordConfig>(it)
                }
            } else {
                LOGGER.info("Config file is empty or malformed, using default values")
                createDefaultConfig()
            }
        } catch (e: Exception) {
            LOGGER.warn("Error loading config, using default values: ${e.message}")
            createDefaultConfig()
        }

        saveConfig()
    }

    private fun createDefaultConfig(): CSNDiscordConfig {
        return CSNDiscordConfig(
            bypassCrossDimension = true,
            botName = "Cobblemon Spawn Notification",
            botAvatarUrl = "https://cdn.modrinth.com/data/LPuJjiQz/a9ef7af8ee170f39131c1b3e7b92237fa07adc56.png"
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun saveConfig() {
        val configFile = File(CONFIG_PATH)
        val prettify = Json {
            prettyPrint = true
        }
        configFile.outputStream().let {
            prettify.encodeToStream(CONFIG, it)
        }
    }
}