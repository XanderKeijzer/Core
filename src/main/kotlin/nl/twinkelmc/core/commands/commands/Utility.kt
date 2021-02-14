package nl.twinkelmc.core.commands.commands

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.WeatherType
import org.bukkit.attribute.Attribute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import java.lang.NumberFormatException

class Utility {

    fun commandPTime(sender: CommandSender, args: ArrayList<String>) {
        if (args.size == 0) {
            sender.sendMessage("Please use /ptime [time] <player>")
            return
        }
        if (args.size == 1) {
            if (sender is Player) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "time_change_self", "{time}", args[0])
                sender.sendMessage(msg)
                try {
                    val time = args[0].toLong()
                    sender.setPlayerTime(time, false)
                } catch (e: NumberFormatException) {
                    when {
                        args[0].contains("day") -> sender.setPlayerTime(6000L, false)
                        args[0].contains("night") -> sender.setPlayerTime(18000L, false)
                        else -> sender.setPlayerTime(6000L, false)
                    }
                }
            } else {
                sender.sendMessage("Please use /ptime [time] [player]")
            }
        } else {
            val target = Main.instance.playerFinder.findPlayer(args[1])
            if (target == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found", "{player}", args[1])
                sender.sendMessage(msg)
            } else {
                try {
                    val time = args[0].toLong()
                    target.setPlayerTime(time, false)
                } catch (e: NumberFormatException) {
                    when {
                        args[0].contains("day") -> target.setPlayerTime(6000L, false)
                        args[0].contains("night") -> target.setPlayerTime(18000L, false)
                        else -> target.setPlayerTime(6000L, false)
                    }
                }
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "time_change_self", "{time}", args[0])
                target.sendMessage(msg)
                if (target != sender) {
                    val msg2 = Main.instance.messageFormatter.getMSGFormat("NL", "time_change_other", "{time}", args[0], "{player}", target.name)
                    sender.sendMessage(msg2)
                }
            }
        }
    }

    fun commandPWeather(sender: CommandSender, args: ArrayList<String>) {
        if (args.size == 0) {
            sender.sendMessage("Please use /ptime [time] <player>")
            return
        }
        if (args.size == 1) {
            if (sender is Player) {
                if (args[0].contains("rain") || args[0].contains("downfall")) {
                    sender.setPlayerWeather(WeatherType.DOWNFALL)
                    val msg = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_self", "{weather}", "rain")
                    sender.sendMessage(msg)
                } else {
                    sender.setPlayerWeather(WeatherType.CLEAR)
                    val msg = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_self", "{weather}", "clear")
                    sender.sendMessage(msg)
                }
            } else {
                sender.sendMessage("Please use /ptime [time] [player]")
            }
        } else {
            val target = Main.instance.playerFinder.findPlayer(args[1])
            if (target == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found", "{player}", args[1])
                sender.sendMessage(msg)
            } else {
                if (args[0].contains("rain") || args[0].contains("downfall")) {
                    target.setPlayerWeather(WeatherType.DOWNFALL)
                    val msg = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_self", "{weather}", "rain")
                    target.sendMessage(msg)
                    if (target != sender) {
                        val msg2 = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_other", "{weather}", "rain", "{player}", target.name)
                        sender.sendMessage(msg2)
                    }
                } else {
                    target.setPlayerWeather(WeatherType.CLEAR)
                    val msg = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_self", "{weather}", "clear")
                    target.sendMessage(msg)
                    if (target != sender) {
                        val msg2 = Main.instance.messageFormatter.getMSGFormat("NL", "weather_change_other", "{weather}", "clear", "{player}", target.name)
                        sender.sendMessage(msg2)
                    }
                }
            }
        }
    }

    fun commandHeal(sender: CommandSender, args: ArrayList<String>) {
        if (args.size == 0) {
            if (sender is Player) {
                sender.health = sender.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.defaultValue ?: 20.0
                for (effect in sender.activePotionEffects)
                    sender.removePotionEffect(effect.type)
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "heal_self")
                sender.sendMessage(msg)
            } else {
                sender.sendMessage("Please use /heal [player]")
            }
        } else {
            val target = Main.instance.playerFinder.findPlayer(args[0])
            if (target == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found", "{player}", args[0])
                sender.sendMessage(msg)
            } else {
                target.health = target.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.defaultValue ?: 20.0
                target.foodLevel = 20
                for (effect in target.activePotionEffects)
                    target.removePotionEffect(effect.type)
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "heal_self")
                target.sendMessage(msg)
                if (target != sender) {
                    val msg2 = Main.instance.messageFormatter.getMSGFormat("NL", "heal_other", "{player}", args[0])
                    sender.sendMessage(msg2)
                }
            }
        }
    }

    fun commandCraft(sender: Player, args: ArrayList<String>) {
        sender.openWorkbench(null, true)
    }

    fun commandInvsee(sender: Player, args: ArrayList<String>) {
        val target = Main.instance.playerFinder.findPlayer(args[0])
        if (target == null) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
            sender.sendMessage(msg)
        } else {
            sender.openInventory(target.inventory)
        }
    }

}
