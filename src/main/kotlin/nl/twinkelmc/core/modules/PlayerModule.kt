package nl.twinkelmc.core.modules

import org.bukkit.entity.Player

class PlayerModule {

    private val playerList = arrayListOf<Player>()

    fun register(player: Player) { playerList.add(player) }
    fun register(players: Collection<Player>) { playerList.addAll(players) }
    fun remove(player: Player) { playerList.remove(player) }
    fun getPlayers(): ArrayList<Player> { return playerList }
    fun contains(player: Player): Boolean { return playerList.contains(player) }

}