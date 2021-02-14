package nl.twinkelmc.core.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.hanging.HangingBreakEvent

class ExplosionEvent: Listener {

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
            event.isCancelled = true
    }

    @EventHandler
    fun onHangingBreak(event: HangingBreakEvent) {
        if (event.cause == HangingBreakEvent.RemoveCause.EXPLOSION)
            event.isCancelled = true
    }

}