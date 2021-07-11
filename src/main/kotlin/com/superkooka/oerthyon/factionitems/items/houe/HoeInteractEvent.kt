package com.superkooka.oerthyon.factionitems.items.houe

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class HoeInteractEvent : Listener {

    @EventHandler
    fun onPlayerInteractWithHoe(event: PlayerInteractEvent) {
        if (Action.LEFT_CLICK_BLOCK != event.action) return
        if (null == event.item) return
        val item = event.item
        if (!EpicHoe.isEpicHoe(item) && !LegendaryHoe.isLegendaryHoe(item)) return

        val block = event.clickedBlock
        if (!isCrop(block)) return

        event.isCancelled = true

        if (!hasPermission(event.player, item)) {
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
            EpicHoe.isEpicHoe(item) -> -1..1
            LegendaryHoe.isLegendaryHoe(item) -> -2..2
            else -> return
        }

        for (xOff in range) {
            for (zOff in range) {
                val relativeBlock = block.getRelative(xOff, 0, zOff)

                if (isCrop(relativeBlock) && relativeBlock.data == maxAge(relativeBlock.type))  { //TODO: Change this deprecated way)
                    val cropMaterial = relativeBlock.type
                    val breakItem = blockToItem(cropMaterial)

                    relativeBlock.breakNaturally(item)

                    if (event.player.inventory.containsAtLeast(breakItem, 1)) {
                        event.player.inventory.removeItem(breakItem)
                        relativeBlock.type = cropMaterial
                    }
                }
            }
        }
    }

    private fun isCrop(block: Block): Boolean {
        return when (block.type) {
            Material.POTATO, Material.CARROT, Material.CROPS, Material.NETHER_WARTS -> true
            else -> false
        }
    }

    private fun maxAge(material: Material): Byte {
        return when (material) {
            Material.CARROT -> 7
            Material.POTATO -> 7
            Material.CROPS -> 7
            Material.NETHER_WARTS -> 3
            else -> throw IllegalArgumentException()
        }
    }

    private fun blockToItem(material: Material): ItemStack {
        return when (material) {
            Material.CARROT -> ItemStack(Material.CARROT_ITEM)
            Material.POTATO -> ItemStack(Material.POTATO_ITEM)
            Material.CROPS -> ItemStack(Material.WHEAT)
            Material.NETHER_WARTS -> ItemStack(Material.NETHER_STALK)
            else -> throw IllegalArgumentException()
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