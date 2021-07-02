package com.superkooka.oerthyon.factionitems.items.houe

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerWalkOnSoulsandEvent : Listener {

    @EventHandler
    fun whenPlayerWalkOnSoulsand(event: PlayerMoveEvent) {
        val player = event.player
        val item = player.itemInHand ?: return
        if (!hasPermission(player, item)) return

        if (Material.SOUL_SAND == event.from.block.type) {
            player.activePotionEffects.forEach { effect ->
                if (effect.type == PotionEffectType.SPEED && effect.amplifier > 1) {
                    return
                }
            }

            player.removePotionEffect(PotionEffectType.SPEED)
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 30, 1))
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