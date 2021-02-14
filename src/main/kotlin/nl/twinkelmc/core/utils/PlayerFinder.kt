package nl.twinkelmc.core.utils

import nl.twinkelmc.core.Main
import org.bukkit.entity.Player

class PlayerFinder {

    fun findPlayer(player: String): Player? {

        for (search in Main.instance.playerModule.getPlayers()) {
            if (search.name.toLowerCase().startsWith(player.toLowerCase()))
                return search
        }
        return null

    }

}