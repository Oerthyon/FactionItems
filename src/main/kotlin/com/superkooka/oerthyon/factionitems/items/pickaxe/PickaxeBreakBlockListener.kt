package com.superkooka.oerthyon.factionitems.items.pickaxe

import com.superkooka.oerthyon.factionitems.Main
import com.superkooka.oerthyon.factionitems.utils.StringUtils
import net.minecraft.server.v1_8_R3.Block
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method


class PickaxeBreakBlockListener : Listener {

    @EventHandler
    fun onPickaxeBreakBlock(event: BlockBreakEvent) {
        val player = event.player
        val tool = player.itemInHand

        val (hasPermission, itemName) = when (true) {
            EpicPickaxe.isEpicPickaxe(tool) -> Pair(player.hasPermission("oerthyon.factionitems.pickaxe.epic.use"), EpicPickaxe.displayName)
            LegendaryPickaxe.isLegendaryPickaxe(tool) -> Pair(player.hasPermission("oerthyon.factionitems.pickaxe.legendary.use"), LegendaryPickaxe.displayName)
            else -> return
        }

        if (!hasPermission) {
            player.sendMessage(
                StringUtils.parse(
                    Main.configuration.getString(
                        "global.messages.not_enough_permission_to_use_it",
                        "Vous n'avez pas la permission d'utiliser cette pioche"
                    ), hashMapOf(Pair("player", player.displayName), Pair("item_name", itemName))
                )
            )
            event.isCancelled = true
            return
        }

        val (range, luckLevel) = when (true) {
            EpicPickaxe.isEpicPickaxe(tool) -> Pair(Triple(-1..1, -1..-1, -1..1), 0)
            LegendaryPickaxe.isLegendaryPickaxe(tool) -> Pair(Triple(-2..2, -1..3, -2..2), LegendaryPickaxe.LUCK_LEVEL)
            else -> return
        }

        for (xOff in range.first) {
            for (yOff in range.second) {
                for (zOff in range.third) {
                    val block = event.block.getRelative(xOff, yOff, zOff)

                    if (!isBreakableBlock(block.type)) continue

                    block.getDrops(tool).forEach { item ->
                        val smeltedItem: ItemStack? = when (item.type) {
                            Material.IRON_ORE -> ItemStack(Material.IRON_INGOT)
                            Material.GOLD_ORE -> ItemStack(Material.GOLD_INGOT)
                            else -> null
                        }

                        val getNMSBlockMethod: Method = CraftBlock::class.java.getDeclaredMethod("getNMSBlock")
                        getNMSBlockMethod.isAccessible = true
                        val nmsBlock: Block = getNMSBlockMethod.invoke(block) as Block
                        var xp = nmsBlock.getExpDrop((block.world as CraftWorld).handle, nmsBlock.blockData, luckLevel)

                        when (smeltedItem) {
                            null -> block.breakNaturally(tool)
                            else -> {
                                block.type = Material.AIR
                                block.world.dropItemNaturally(block.location, smeltedItem)
                                xp += 1 // Officially 0.7
                            }
                        }

                        if (0 != xp) {
                            val orb =
                                block.world.spawnEntity(block.location, EntityType.EXPERIENCE_ORB) as ExperienceOrb
                            orb.experience = xp
                        }
                    }
                }
            }
        }
    }

    fun isBreakableBlock(material: Material): Boolean {
        return when (material) {
            Material.BARRIER -> false
            Material.BEDROCK -> false
            Material.COMMAND -> false
            Material.ENDER_PORTAL -> false
            Material.ENDER_PORTAL_FRAME -> false
            Material.PORTAL -> false
            else -> true
        }
    }
}