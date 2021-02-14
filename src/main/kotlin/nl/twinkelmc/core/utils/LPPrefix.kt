package nl.twinkelmc.core.utils

import net.luckperms.api.model.user.UserManager
import nl.twinkelmc.core.Main
import org.bukkit.entity.Player

class LPPrefix {

    fun getPrefix(p: Player): String {
        if (Main.instance.api != null) {
            val userManager: UserManager = Main.instance.api!!.userManager
            val user = userManager.getUser(p.uniqueId)
            if (user != null) {
                val handler = user.cachedData
                if (handler.metaData.prefix != null) {
                    return handler.metaData.prefix!!.replace("&", "§")
                }
            }
        }
        return ""
    }

    fun getPrefixColor(p: Player): String {
        val prefix = getPrefix(p).substring(0, 2)
        return if (prefix.startsWith("§")) prefix else "§7"
    }

}