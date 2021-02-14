package nl.twinkelmc.core.commands

//import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import nl.twinkelmc.core.Main
import nl.twinkelmc.core.utils.MapRenderer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

class CommandHandler: CommandExecutor {

    private val chat = Main.instance.classInstance.chat
    private val gameMode = Main.instance.classInstance.gameMode
    private val give = Main.instance.classInstance.give
    private val mobility = Main.instance.classInstance.mobility
    private val teleport = Main.instance.classInstance.teleport
    private val utility = Main.instance.classInstance.utility

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        val list: ArrayList<String> = arrayListOf()
        for (arg in args) {
            list.add(arg)
        }

        if (sender is Player) {

            if (!sender.hasPermission("tmc.core")) {
                val msg = Main.instance.messageFormatter.getMSGFormat("NL", "no_permission")
                sender.sendMessage(msg)
                return false
            }

            when (label.toLowerCase()) {

                "area" -> {
                    val itemStack = ItemStack(Material.STICK)
                    val itemMeta = itemStack.itemMeta
                    itemMeta?.setDisplayName("§cAreaSelector")
                    itemStack.itemMeta = itemMeta
                    sender.inventory.addItem(itemStack)
                }
                "render" -> render(sender)
                "fly" -> mobility.commandFly(sender, list)
                "up" -> mobility.commandUp(sender)
                "top" -> mobility.commandTop(sender)
                "speed" -> mobility.commandSpeed(sender, list)
                "tp" -> teleport.commandTP(sender, list)
                "tphere" -> teleport.commandTPHere(sender, list)
                "back" -> teleport.commandBack(sender)
                "craft" -> utility.commandCraft(sender, list)
                "ptime" -> utility.commandPTime(sender, list)
                "pweather" -> utility.commandPWeather(sender, list)
                "invsee" -> utility.commandInvsee(sender, list)
                "give" -> give.commandGive(sender, list)
                "help" -> chat.commandHelp(sender, list)
                "list" -> chat.commandList(sender, list)
                "msg" -> chat.commandMsg(sender, list)
                "rules" -> chat.commandRules(sender, list)
                "heal" -> utility.commandHeal(sender, list)
                "gm" -> gameMode.commandGameMode(sender, list)
                "gamemode" -> gameMode.commandGameMode(sender, list)
                "spawn" -> sender.teleport(Location(Bukkit.getWorld("world")!!, -160.5, 81.0, -463.5))
                "gms" -> {
                    val ls = arrayListOf("0")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gmc" -> {
                    val ls = arrayListOf("1")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gma" -> {
                    val ls = arrayListOf("2")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gmsp" -> {
                    val ls = arrayListOf("3")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
            }
        } else {
            when (label.toLowerCase()) {
                "fly" -> mobility.commandFly(sender, list)
                "speed" -> mobility.commandSpeed(sender, list)
                "tp" -> teleport.commandTP(sender, list)
                "ptime" -> utility.commandPTime(sender, list)
                "pweather" -> utility.commandPWeather(sender, list)
                "give" -> give.commandGive(sender, list)
                "help" -> chat.commandHelp(sender, list)
                "list" -> chat.commandList(sender, list)
                "msg" -> chat.commandMsg(sender, list)
                "rules" -> chat.commandRules(sender, list)
                "heal" -> utility.commandHeal(sender, list)
                "gm" -> gameMode.commandGameMode(sender, list)
                "gamemode" -> gameMode.commandGameMode(sender, list)
                "gms" -> {
                    val ls = arrayListOf("0")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gmc" -> {
                    val ls = arrayListOf("1")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gma" -> {
                    val ls = arrayListOf("2")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
                "gmsp" -> {
                    val ls = arrayListOf("3")
                    ls.addAll(args)
                    gameMode.commandGameMode(sender, ls)
                }
            }
        }
        return true
    }

    private fun render(player: Player) {
        val renderer = MapRenderer(player,"Efteling_Plattegrond", player.location)
        renderer.render()
    }

//    private fun test(list: ArrayList<String>) {
//        csvReader().open("plugins/TMC-Core/${list[0]}.csv") {
//            readAllAsSequence().forEach { row: List<String> ->
//                Bukkit.broadcastMessage("$row")
//            }
//        }
//    }

}