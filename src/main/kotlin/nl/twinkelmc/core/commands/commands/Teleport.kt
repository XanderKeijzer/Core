package nl.twinkelmc.core.commands.commands

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class Teleport {

    private val lastLocationMap = hashMapOf<Player, Location>()

    fun commandTPHere(player: Player, args: ArrayList<String>) {
        val target = Main.instance.playerFinder.findPlayer(args[0])
        if (target == null) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
            player.sendMessage(msg)
        } else if (target == player) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_to_self")
            player.sendMessage(msg)
        } else {
            lastLocationMap[target] = target.location
            target.teleport(player)
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_here_other","{player}", target.name)
            player.sendMessage(msg)
            val msg2 = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_here_self","{player}", player.name)
            target.sendMessage(msg2)
        }
    }

    fun commandBack(player: Player) {
        if (lastLocationMap.containsKey(player)) {
            player.teleport(lastLocationMap[player]!!)
            lastLocationMap[player] = player.location
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_back")
            player.sendMessage(msg)
        } else {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_no_back")
            player.sendMessage(msg)
        }
    }

    fun commandTP(sender: CommandSender, args: ArrayList<String>) {
        if (sender is Player) {
            when(args.size) {
                1 -> {
                    val target = Main.instance.playerFinder.findPlayer(args[0])
                    if (target == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                        sender.sendMessage(msg)
                    } else {
                        teleport(sender, sender, target)
                    }
                }
                2 -> {
                    val recipient = Main.instance.playerFinder.findPlayer(args[0])
                    if (recipient == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                        sender.sendMessage(msg)
                        return
                    }
                    val target = Main.instance.playerFinder.findPlayer(args[1])
                    if (target == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[1])
                        sender.sendMessage(msg)
                        return
                    }
                    teleport(recipient, sender, target)
                }
                3 -> {
                    teleport(sender, sender, Location(sender.world, args[0].formatDouble(), args[1].formatDouble(), args[2].formatDouble()))
                }
                4 -> {
                    val recipient = Main.instance.playerFinder.findPlayer(args[0])
                    if (recipient == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                        sender.sendMessage(msg)
                        return
                    }
                    teleport(recipient, sender, Location(recipient.world, args[1].formatDouble(), args[2].formatDouble(), args[3].formatDouble()))
                }
                else -> sender.sendMessage("Please use: /tp <player|location> [<player>|<location>]")
            }
        } else {
            when(args.size) {
                2 -> {
                    val recipient = Main.instance.playerFinder.findPlayer(args[0])
                    if (recipient == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                        sender.sendMessage(msg)
                        return
                    }
                    val target = Main.instance.playerFinder.findPlayer(args[1])
                    if (target == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[1])
                        sender.sendMessage(msg)
                        return
                    }
                    teleport(recipient, sender, target)
                }
                4 -> {
                    val recipient = Main.instance.playerFinder.findPlayer(args[0])
                    if (recipient == null) {
                        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                        sender.sendMessage(msg)
                        return
                    }
                    teleport(recipient, sender, Location(recipient.world, args[1].formatDouble(), args[2].formatDouble(), args[3].formatDouble()))
                }
                else -> sender.sendMessage("Please use: /tp <player|location> [<player>|<location>]")
            }
        }
    }

    private fun teleport(player: Player, executor: CommandSender, target: Player) {
        if (player != executor) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_other", "{target}", target.name, "{player}", player.name)
            executor.sendMessage(msg)
        }
        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_self", "{target}", target.name)
        player.sendMessage(msg)
        lastLocationMap[player] = player.location
        player.teleport(target)
    }

    private fun teleport(player: Player, executor: CommandSender, target: Location) {
        if (player != executor) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_other", "{target}", "X: ${target.x}, Y: ${target.y}, Z: ${target.z}", "{player}", player.name)
            executor.sendMessage(msg)
        }
        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_self", "{target}", "X: ${target.x}, Y: ${target.y}, Z: ${target.z}")
        player.sendMessage(msg)
        lastLocationMap[player] = player.location
        player.teleport(target)
    }

    private fun String.formatDouble(): Double {
        return try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

}