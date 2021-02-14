package nl.twinkelmc.core.commands.commands

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.lang.NumberFormatException
import javax.sound.midi.MidiDeviceReceiver
import kotlin.math.max
import kotlin.math.min

class Mobility {

    fun commandUp(player: Player) {
        val loc = player.location
        var state = 0
        for (i in loc.y.toInt()..256) {
            when(state) {
                0 -> { if (!loc.block.isPassable) state = 1 }
                1 -> { if ( loc.block.isPassable) state = 2 }
                2 -> { if (loc.block.isPassable) { state = 3; break } else { state = 1 } }
            }
            loc.y++
        }
        if (state == 3) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_up")
            player.sendMessage(msg)
            player.teleport(loc.add(0.0, -1.0, 0.0))
        } else {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "no_floor_above")
            player.sendMessage(msg)
        }
    }

    fun commandTop(player: Player) {
        val loc = player.location
        loc.y = 256.0
        var state = false
        for (i in player.location.y.toInt()..256) {
            if (!loc.block.isPassable) { state = true; break }
            loc.y--
        }
        if (state && loc.y.toInt() != player.location.y.toInt()) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "teleport_top")
            player.sendMessage(msg)
            player.teleport(loc.add(0.0, 1.0, 0.0))
        } else {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "no_floor_above")
            player.sendMessage(msg)
        }
    }

    fun commandFly(sender: CommandSender, args: ArrayList<String>) {
        if (args.size > 0) {
            val player = Main.instance.playerFinder.findPlayer(args[0])
            if (player == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[0])
                sender.sendMessage(msg)
                return
            }
            setFly(sender, player)
        } else {
            if (sender is Player) { setFly(sender, sender) }
            else { sender.sendMessage("Please use /fly [player]") }
        }
    }

    private fun setFly(sender: CommandSender, receiver: Player) {
        receiver.allowFlight = !receiver.allowFlight
        if (receiver.allowFlight) {
            receiver.velocity = receiver.velocity.setY(0.001)
            val bukkitRunnable = object : BukkitRunnable() { override fun run() { receiver.isFlying = true } }
            bukkitRunnable.runTaskLater(Main.instance, 1)
        }
        if (sender != receiver) {
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "fly_change_other","{player}", receiver.name, "{state}", Main.instance.messageFormatter.getState("NL", receiver.allowFlight))
            sender.sendMessage(msg)
        }
        val msg = Main.instance.messageFormatter.getMSGFormat("NL", "fly_change_self", "{state}", Main.instance.messageFormatter.getState("NL", receiver.allowFlight))
        receiver.sendMessage(msg)

    }

    fun commandSpeed(sender: CommandSender, args: ArrayList<String>) {
        if (args.size == 2) {
            val receiver = Main.instance.playerFinder.findPlayer(args[1])
            if (receiver == null) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "player_not_found","{player}", args[1])
                sender.sendMessage(msg)
                return
            }
            setSpeed(executor = sender, receiver = receiver, args[0])
        } else if (sender is Player && args.size != 0) { setSpeed(executor = sender, receiver = sender, args[0])
        } else { sender.sendMessage("Please use /speed [speed] [player]") }
    }

    private fun setSpeed(executor: CommandSender, receiver: Player, speed: String) {
        var speedNum = 0.0F
        try{ speedNum = speed.toFloat() }
        catch (e: NumberFormatException) { executor.sendMessage("Unknown speed value $speed") }
        if (receiver.isFlying) {
            receiver.flySpeed = max(min(speedNum/10, 1.0F),-1.0F)
            if (executor !is Player || executor != receiver) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "fly_speed_other","{player}",receiver.name,"{state}",(receiver.flySpeed*10).toString())
                executor.sendMessage(msg)
            }
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "fly_speed_self", "{state}", (receiver.flySpeed*10).toString())
            receiver.sendMessage(msg)
        } else {
            receiver.walkSpeed = max(min(speedNum/5, 1.0F),-1.0F)
            if (executor !is Player || executor != receiver) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "walk_speed_other","{player}",receiver.name,"{state}",(receiver.walkSpeed*5).toString())
                executor.sendMessage(msg)
            }
            val msg = Main.instance.messageFormatter.getMSGFormat("NL", "walk_speed_self", "{state}", (receiver.walkSpeed*5).toString())
            receiver.sendMessage(msg)
        }

    }

}