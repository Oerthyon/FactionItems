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
        val playersWalkSpeed = arrayListOf<UUID>()
    }

    @EventHandler
    fun whenPlayerWalkOnSoulsand(event: PlayerMoveEvent) {
        val player = event.player
        val item = player.itemInHand

        if (Material.SOUL_SAND == event.from.block.type && !playersWalkSpeed.contains(player.uniqueId) && hasPermission(player, item)) {
            player.walkSpeed /= 0.4f
            playersWalkSpeed.add(player.uniqueId)
        } else if (Material.SOUL_SAND != event.from.block.type && Material.AIR != event.from.block.type && playersWalkSpeed.contains(player.uniqueId)) {
            player.walkSpeed *= 0.4f
            playersWalkSpeed.remove(player.uniqueId)
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