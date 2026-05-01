package example.traktbot

import kotlin.uuid.Uuid

sealed interface Config {
    data class Bot(
        val token: String,
        val botId: Uuid?,
    ) {
        companion object {
            fun fromEnvironment(environment: Map<String, String> = System.getenv()): Bot {
                val token = environment.required("TRAQ_BOT_TOKEN")
                val botId =
                    environment.optional("TRAQ_BOT_ID")?.let { rawBotId ->
                        runCatching { Uuid.parse(rawBotId) }.getOrElse { cause ->
                            throw IllegalArgumentException("TRAQ_BOT_ID must be a valid UUID.", cause)
                        }
                    }

                return Bot(token = token, botId = botId)
            }
        }
    }

    data class SelfBot(
        val token: String,
    ) {
        companion object {
            fun fromEnvironment(environment: Map<String, String> = System.getenv()): SelfBot {
                val token = environment.required("TRAQ_USER_TOKEN")

                return SelfBot(token = token)
            }
        }
    }
}

private fun Map<String, String>.required(key: String): String =
    this[key]?.takeIf(String::isNotBlank) ?: throw IllegalStateException("$key is required.")

private fun Map<String, String>.optional(key: String): String? = this[key]?.takeIf(String::isNotBlank)
