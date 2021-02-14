package nl.twinkelmc.core.events

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectionEvent: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {

        Main.instance.playerModule.register(event.player)
        val prefix = Main.instance.prefix.getPrefixColor(event.player)
        event.player.setPlayerListHeaderFooter("§c§lTwinkel§r§cMC §7www.twinkelmc.nl", "§7Meer dan magie!")
        event.player.setPlayerListName(prefix + event.player.name)

        val name: String = event.player.name
        event.joinMessage = if (event.player.hasPlayedBefore()) "§a+ §7$name" else "§7Welkom §e$name§7 op TwinkelMC!"

        Main.instance.teamManager.setPlayerScoreboard(event.player)

        if (!event.player.hasPermission("tmc.core"))
            event.player.teleport(Location(Bukkit.getWorld("world")!!, -160.5, 81.0, -463.5))

    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {

        Main.instance.playerModule.remove(event.player)

        event.quitMessage = "§c- §7${event.player.name}"

        Main.instance.teamManager.removePlayerScoreboard(event.player)

    }

}