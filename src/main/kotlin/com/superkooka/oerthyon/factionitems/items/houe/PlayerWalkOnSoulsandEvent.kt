package com.superkooka.oerthyon.factionitems.items.houe

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class PlayerWalkOnSoulsandEvent : Listener {

    companion object {
        @JvmStatic
        val playersOnSoulsand = hashMapOf<UUID, Float>() // Save original walkspeed for player before change it
    }

    @EventHandler
    fun whenPlayerWalkOnSoulsand(event: PlayerMoveEvent) {
        val player = event.player
        val item = player.itemInHand ?: return
        if (!hasPermission(player, item)) return

        print(player.walkSpeed)

        if (Material.SOUL_SAND == event.from.block.type) {
            if (!playersOnSoulsand.containsKey(player.uniqueId)) {
                playersOnSoulsand[player.uniqueId] = player.walkSpeed
                player.walkSpeed = 0.5F
            }
        } else {
            if (playersOnSoulsand.containsKey(player.uniqueId)) {
                player.walkSpeed = playersOnSoulsand[player.uniqueId] ?: 0.2F
                playersOnSoulsand.remove(player.uniqueId)
            }
        }
    }

    private fun hasPermission(player: Player, hoe: ItemStack): Boolean {
        return when (true) {
            EpicHoe.isEpicHoe(hoe) && player.hasPermission("oerthyon.factionitems.hoe.epic.use") -> true
            LegendaryHoe.isLegendaryHoe(hoe) && player.hasPermission("oerthyon.factionitems.hoe.legendary.use") -> true
            else -> false
        }
    }
}