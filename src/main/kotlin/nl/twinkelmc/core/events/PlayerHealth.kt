package nl.twinkelmc.core.events

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

class PlayerHealth: Listener {

    @EventHandler
    fun onHunger(e: FoodLevelChangeEvent) {
        if (e.entity is Player) {
            e.isCancelled = true
            (e.entity as Player).foodLevel = 20
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (e.entity is Player) {
            e.isCancelled = true
            (e.entity as Player).health = (e.entity as Player).getAttribute(Attribute.GENERIC_MAX_HEALTH)?.defaultValue ?: 20.0
        }
    }

}