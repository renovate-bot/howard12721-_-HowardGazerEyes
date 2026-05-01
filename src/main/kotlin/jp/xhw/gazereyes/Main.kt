package jp.xhw.gazereyes

import jp.xhw.trakt.bot.context.base.addStamp
import jp.xhw.trakt.bot.context.base.fetch
import jp.xhw.trakt.bot.model.UserMessageCreated
import jp.xhw.trakt.bot.model.stamp
import jp.xhw.trakt.bot.selfTrakt

suspend fun main() {
    val config = Config.SelfBot.fromEnvironment()

    val client =
        selfTrakt(
            token = config.token,
        ) {
            val gazerWords =
                listOf(
                    "howard",
                    "はわーど",
                    "ひろふみ",
                    "Howard",
                )

            on<UserMessageCreated> { event ->
                val message = event.message.fetch()
                val author = message.author.fetch()

                if (!author.isBot && gazerWords.any { it in message.content }) {
                    repeat(10) {
                        message.addStamp(
                            stamp("4e4c3c0b-2a23-439d-98b1-2fa3ef5caf40"),
                            100,
                        )
                    }
                }
            }
        }

    try {
        client.start()
    } finally {
        client.stop()
    }
}
