package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.Main
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class PlayerWalkOnSoulsandEvent : Listener {

    companion object {
        @JvmStatic
        val playerWithOurSpeedEffect = arrayListOf<UUID>()
    }

    @EventHandler
    fun whenPlayerWalkOnSoulsand(event: PlayerMoveEvent) {
        val player = event.player
        val item = player.itemInHand ?: return
        if(event.from.x == event.to.x && event.from.y == event.to.y && event.from.z == event.from.z) return // Yaw and Pitch also trigger this event
        if (!hasPermission(player, item)) return


        if (Material.SOUL_SAND == event.from.block.type) {
            player.activePotionEffects.forEach { effect ->
                if (effect.type == PotionEffectType.SPEED && !playerWithOurSpeedEffect.contains(player.uniqueId)) {
                    return
                }
            }

            player.removePotionEffect(PotionEffectType.SPEED)
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 10, 1))

            playerWithOurSpeedEffect.add(player.uniqueId)
            Bukkit.getScheduler().runTaskLater(Main.instance, {
                playerWithOurSpeedEffect.remove(player.uniqueId)
            }, 10)
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