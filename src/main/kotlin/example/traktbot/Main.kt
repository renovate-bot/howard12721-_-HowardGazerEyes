package example.traktbot

import jp.xhw.trakt.bot.context.base.reply
import jp.xhw.trakt.bot.model.BotMessageCreated
import jp.xhw.trakt.bot.trakt

suspend fun main() {
    val config = Config.Bot.fromEnvironment()

    val client =
        trakt(
            token = config.token,
            botId = config.botId,
        ) {
            on<BotMessageCreated> { event ->
                val message = event.message
                if (message.content.startsWith("!ping")) {
                    message.reply("pong!")
                }
            }
        }

    /*

    セルフボットの場合

    val config = Config.SelfBot.fromEnvironment()

    val client =
        selfTrakt(
            token = config.token,
        ) {
            on<UserMessageCreated> { event ->
                val message = event.message.fetch()
                if (message.content.startsWith("!ping")) {
                    message.reply("pong!")
                }
            }
        }

     */

    try {
        client.start()
    } finally {
        client.stop()
    }
}
