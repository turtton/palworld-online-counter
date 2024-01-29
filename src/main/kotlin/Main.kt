package net.turtton

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.edit
import dev.kord.core.entity.channel.VoiceChannel
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.minutes

val DISCORD_TOKEN = "DISCORD_TOKEN"
val DISCORD_CHANNEL = "DISCORD_CHANNEL"
val SHOW_PLAYER_COMMAND = "SHOW_PLAYER_COMMAND"

suspend fun main() {
    val env = System.getenv()
    val token = env[DISCORD_TOKEN]
    checkNotNull(token) { "$DISCORD_TOKEN environment variable not set" }
    val channel = env[DISCORD_CHANNEL]
    checkNotNull(channel) { "$DISCORD_CHANNEL environment variable not set" }
    val command = env[SHOW_PLAYER_COMMAND]
    checkNotNull(command) { "$SHOW_PLAYER_COMMAND environment variable not set" }

    val kord = Kord(token)

    kord.on<ReadyEvent> {
        kord.launch {
            while (true) {
                val names =
                    runCatching {
                        ProcessBuilder(*command.split(" ").toTypedArray())
                            .redirectOutput(ProcessBuilder.Redirect.PIPE)
                            .redirectError(ProcessBuilder.Redirect.PIPE)
                            .start().also {
                                it.waitFor(10, TimeUnit.SECONDS)
                            }
                    }.fold(
                        onSuccess = { process ->
                            val result = process.inputStream.bufferedReader().readText()
                            println("Result: $result")
                            result.split("\n").filter { it.isNotBlank() }.mapNotNull { it.split(",").firstOrNull() }
                        },
                        onFailure = {
                            println("Failed to run command: $command")
                            it.printStackTrace()
                            null
                        },
                    )
                runCatching {
                    kord.getChannelOf<VoiceChannel>(Snowflake(channel))?.edit {
                        val count = names?.size?.toString() ?: "???"
                        name = "Online: $count"
                        topic = names?.joinToString(", ") ?: "???"
                    }
                }.onFailure {
                    println("Failed to update channel: $channel")
                    it.printStackTrace()
                }
                delay(15.minutes)
            }
        }
    }
    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}
