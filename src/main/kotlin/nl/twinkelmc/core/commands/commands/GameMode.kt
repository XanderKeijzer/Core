package nl.twinkelmc.core.commands.commands

import nl.twinkelmc.core.Main
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class GameMode {

    fun commandGameMode(sender: CommandSender, args: ArrayList<String>) {

        if (args.size == 2) {
            val receiver = Main.instance.playerFinder.findPlayer(args[1])
            if (receiver == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[1])
                sender.sendMessage(msg)
                return
            }
            setGameMode(executor = sender, receiver = receiver, args[0])
        } else if (sender is Player && args.size != 0) {
            setGameMode(executor = sender, receiver = sender, args[0])
        } else {
            sender.sendMessage("Please use /gm [gamemode] [player]")
        }

    }

    private fun setGameMode(executor: CommandSender, receiver: Player, gamemode: String) {

        val gameMode = findGameMode(gamemode)
        val msgMode = "$gameMode".toLowerCase()
        if (gameMode == null) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "gamemode_not_found", "{state}", gamemode)
            executor.sendMessage(msg)
            return
        }

        receiver.gameMode = gameMode
        if (executor !is Player || executor != receiver) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "gamemode_change_other","{player}",receiver.name,"{state}",msgMode)
            executor.sendMessage(msg)
        }
        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "gamemode_change_self", "{state}", msgMode)
        receiver.sendMessage(msg)

    }


    private fun findGameMode(gamemode: String): GameMode? {

        when (gamemode) {
            "0" -> return GameMode.SURVIVAL
            "1" -> return GameMode.CREATIVE
            "2" -> return GameMode.ADVENTURE
            "3" -> return GameMode.SPECTATOR
        }

        if (gamemode.startsWith("sp"))
            return GameMode.SPECTATOR
        if (gamemode.startsWith("c"))
            return GameMode.CREATIVE
        if (gamemode.startsWith("s"))
            return GameMode.SURVIVAL
        if (gamemode.startsWith("a"))
            return GameMode.ADVENTURE

        return null

    }

}