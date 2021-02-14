package nl.twinkelmc.core.commands.commands

import nl.twinkelmc.core.Main
import org.bukkit.command.CommandSender

class Chat {

    fun commandList(sender: CommandSender, args: ArrayList<String>) {
        var msg = Main.instance.messageFormatter.getMSGFormat("NL", "list_title", "{amount}", Main.instance.playerModule.getPlayers().size.toString())
        sender.sendMessage("")
        if (Main.instance.playerModule.getPlayers().size == 1)
            msg = msg.replace("spelers", "speler")
        sender.sendMessage(msg)
        sender.sendMessage("§8§m-------------------------------------------")
        sender.sendMessage("")
        var send = ""
        for (player in Main.instance.playerModule.getPlayers()) {
            send = if (send == "") {
                "§7${Main.instance.prefix.getPrefix(player)}${player.name}"
            } else {
                "$send§7, ${Main.instance.prefix.getPrefix(player)}${player.name}"
            }
            if (send.length > 100) {
                sender.sendMessage(send)
                send = ""
            }
        }
        if (send != "")
            sender.sendMessage(send)
        sender.sendMessage("")
        sender.sendMessage("§8§m-------------------------------------------")

    }

    fun commandMsg(sender: CommandSender, args: ArrayList<String>) {

    }

    fun commandRules(sender: CommandSender, args: ArrayList<String>) {

    }

    fun commandHelp(sender: CommandSender, args: ArrayList<String>) {

    }

}