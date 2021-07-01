package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.CropState
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Crops

class HoeInteractEvent : Listener {

    @EventHandler
    fun onPlayerInteractWithHoe(event: PlayerInteractEvent) {
        if (Action.LEFT_CLICK_BLOCK != event.action) return
        if (null == event.item) return
        if (!EpicHoe.isEpicHoe(event.item) && !LegendaryHoe.isLegendaryHoe(event.item)) return

        val block = event.clickedBlock
        if (!isCrop(block)) return
        if ((block.state.data as Crops).state != CropState.RIPE) return

        event.isCancelled = true

        if (!hasPermission(event.player, event.item)) {
            event.player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "global.messages.not_enough_permission_to_use_it",
                        "Vous n'avez pas la permission d'utiliser cet item"
                    ),
                    hashMapOf(
                        Pair("player_name", event.player.displayName),
                        Pair("item_name", event.item.itemMeta.displayName)
                    )
                )
            )
            return
        }

        val range = when (true) {
            EpicHoe.isEpicHoe(event.item) -> -1..1
            LegendaryHoe.isLegendaryHoe(event.item) -> -2..2
            else -> return
        }

        for (xOff in range) {
            for (zOff in range) {
                val relativeBlock = block.getRelative(xOff, 0, zOff)
                val materialOfRelativeBlock = relativeBlock.type
                if (isCrop(relativeBlock) && ((relativeBlock.state.data as Crops).state == CropState.RIPE)) {
                    relativeBlock.breakNaturally()
                    relativeBlock.type = materialOfRelativeBlock
                }
            }
        }
    }

    private fun isCrop(block: Block): Boolean {
        return when (block.type) {
            Material.POTATO, Material.CARROT, Material.PUMPKIN, Material.MELON_BLOCK, Material.CROPS -> true
            else -> false
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
