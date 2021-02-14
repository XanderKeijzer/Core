package nl.twinkelmc.core.modules

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class TeamManager {

    private val manager = Bukkit.getScoreboardManager()

    fun setPlayerScoreboard(p: Player) {
        val board = Bukkit.getScoreboardManager()?.mainScoreboard
        if (board != null) {
            var team = board.getTeam(p.uniqueId.toString().replace("-", "").substring(0, 16))
            if (team == null) {
                team = board.registerNewTeam(p.uniqueId.toString().replace("-", "").substring(0, 16))
                val color = ChatColor.getByChar(Main.instance.prefix.getPrefix(p).substring(1, 2))
                if (color != null) team.color = color
            }
            team.addEntry(p.name)
        }
    }

    fun removePlayerScoreboard(p: Player) {
        val board = Bukkit.getScoreboardManager()?.mainScoreboard
        if (board != null) {
            val team = board.getTeam(p.uniqueId.toString().replace("-", "").substring(0, 16))
            if (team != null) {
                team.removeEntry(p.name)
                team.unregister()
            }
        }
    }

    fun resetAllTeams() {
        val board = Bukkit.getScoreboardManager()?.mainScoreboard
        if (board != null) {
            for (team in board.teams)
                team.unregister()
        }
        for (player in Main.instance.playerModule.getPlayers()) {
            setPlayerScoreboard(player)
        }
    }

}