package nl.twinkelmc.core.events

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatEvent: Listener {

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {

        event.isCancelled = true

        val prefix = Main.instance.prefix.getPrefix(event.player).replace(" ", "")

        for (player in Main.instance.playerModule.getPlayers()) {

            player.sendMessage("$prefix §7${event.player.name} §8► §f${event.message}")

        }

    }

}