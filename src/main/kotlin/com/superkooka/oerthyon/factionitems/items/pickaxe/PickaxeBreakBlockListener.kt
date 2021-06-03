package com.superkooka.oerthyon.factionitems.items.pickaxe

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
        val tool = event.player.itemInHand

        val (range, luckLevel) = when (true) {
            EpicPickaxe.isEpicPickaxe(tool) -> Pair(Triple(-1..1, -1..-1, -1..1), 0)
            LegendaryPickaxe.isLegendaryPickaxe(tool) -> Pair(Triple(-2..2, -1..3, -2..2), LegendaryPickaxe.LUCK_LEVEL)
            else -> return
        }

        for (xOff in range.first) {
            for (yOff in range.second) {
                for (zOff in range.third) {
                    val block = event.block.getRelative(xOff, yOff, zOff)
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
}